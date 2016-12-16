package com.excilys.formation.persistence.computerdao.computerdaoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.PersistenceMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import com.excilys.formation.persistence.connectionprovider.HikariConnectionProvider;

/**
 * @author Euronia
 * 
 */

@Repository
public class ComputerDaoImpl implements ComputerDao {

    ////////// Attributes //////////

    
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private static HikariConnectionProvider connectionProvider;
    private static Logger logger;
    private static ComputerDaoImpl COMPUTER_DAO_INSTANCE;
    public static final String SELECT_JOIN_COMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id";
    public static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
    public static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ;";
    public static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
    public static final String COUNT_ALL = "SELECT COUNT(*) as total FROM computer";
    public static final String COUNT_ALL_FILTERED = "SELECT COUNT(*) as total FROM ( SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ? ) AS derivedTable";
    public static final String DELETE_FROM_COMPANY_COMPUTER = "DELETE FROM computer WHERE computer.company_id = ?";
    static {
        connectionProvider = HikariConnectionProvider.getInstance();
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
        COMPUTER_DAO_INSTANCE = new ComputerDaoImpl();
    }

    public static ComputerDaoImpl getInstance() {
        return COMPUTER_DAO_INSTANCE;
    }
    ////////// Getters and Setters //////////

    public ComputerDaoImpl getComputerDao() {
        return COMPUTER_DAO_INSTANCE;
    }
    
    public void setComputerDao(ComputerDaoImpl d) {
        COMPUTER_DAO_INSTANCE = d;
    }
    
    public HikariConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ////////// Methods //////////

    public Page<Computer> getPage(Page<Computer> page, PageConstraints constraints) throws PersistenceException {
        List<Computer> computers = new ArrayList<Computer>();
        String query = SELECT_JOIN_COMPUTER + getConstraintsString(constraints) + " LIMIT ? OFFSET ? ";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (constraints.getConstraint().containsKey("filter") && constraints.getConstraint().containsKey("orderBy")) {
                preparedStatement.setString(1, constraints.getConstraint().get("filter"));
                preparedStatement.setString(2, constraints.getConstraint().get("filter"));
                preparedStatement.setString(3, constraints.getConstraint().get("orderBy"));
                preparedStatement.setInt(4, page.getElementsByPage());
                preparedStatement.setInt(5, (page.getCurrentPage() - 1) * page.getElementsByPage());
            } else if (constraints.getConstraint().containsKey("orderBy")) {
                preparedStatement.setString(1, constraints.getConstraint().get("orderBy"));
                preparedStatement.setInt(2, page.getElementsByPage());
                preparedStatement.setInt(3, (page.getCurrentPage() - 1) * page.getElementsByPage());
            } else if (constraints.getConstraint().containsKey("filter")) {
                preparedStatement.setString(1, constraints.getConstraint().get("filter"));
                preparedStatement.setString(2, constraints.getConstraint().get("filter"));
                preparedStatement.setInt(3, page.getElementsByPage());
                preparedStatement.setInt(4, (page.getCurrentPage() - 1) * page.getElementsByPage());
            } else {
                preparedStatement.setInt(1, page.getElementsByPage());
                preparedStatement.setInt(2, (page.getCurrentPage() - 1) * page.getElementsByPage());
            } 
            ResultSet resultSet = preparedStatement.executeQuery();
            computers = PersistenceMapper.mapResultsToComputerList(resultSet);
            page.setElements(computers);
            page.setTotalElements(count(constraints));
            resultSet.close();
        } catch (SQLException e) {
            logger.error(
                    "ComputerDaoImpl : getPage(Page<Computer>, PageConstraints) catched SQLException and throwed PersistenceException ");
            throw new PersistenceException("La requete getPage() de DAOComputer a échouée", e);
        }
        return page;
    }

    private String getConstraintsString(PageConstraints constraints) {
        Map<String, String> constraintsMap = constraints.getConstraint();
        StringBuilder returnString = new StringBuilder();
        if (constraintsMap.containsKey("filter")) {
            returnString.append(" WHERE computer.name LIKE ? OR company.name LIKE ? ");
        }
        if (constraintsMap.containsKey("orderBy")) {
            returnString.append(" ORDER BY ? ");
        }
        return returnString.toString();
    }

    private int count() {
        int total = 0;
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            resultSet.next();
            total = resultSet.getInt("total");
            resultSet.close();
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : count() catched SQLException");
            logger.error(e.getStackTrace().toString());
        }
        return total;
    }
            
    private int count(PageConstraints constraints) {
        int total = 0;
        String newQuery = "SELECT COUNT(*) as total FROM ( " + SELECT_JOIN_COMPUTER + getConstraintsString(constraints) +  " ) AS derivedTable";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(newQuery)) {
            if (constraints.getConstraint().containsKey("filter")) {
                preparedStatement.setString(1, constraints.getConstraint().get("filter"));
                preparedStatement.setString(2, constraints.getConstraint().get("filter"));      
            } 
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            total = resultSet.getInt("total");
            resultSet.close();
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : count(PageConstraints) catched SQLException");
            logger.error(e.getStackTrace().toString());
        }
        return total;
    }

    /**
     * A method that gets an entry in the computer table using its id.
     *
     * @param pid The id of the wanted entry. If it's not a valid id a null
     *            computer with an id of 0 will be returned.
     * @return the wanted entry on our computer table, can return a null
     *         computer for a non valid requested id.
     */
    public Computer getById(long pid) {
        Computer returnComputer = null; // Initialization in case of Exception
        String query = SELECT_JOIN_COMPUTER + " WHERE computer.id=?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, pid);
            ResultSet results;
            results = ps.executeQuery();
            returnComputer = PersistenceMapper.mapResultToComputer(results);
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : getById(int) catched SQLException");
            logger.error(e.getStackTrace().toString());
        }
        return returnComputer;
    } 

    @Override
    public Computer getByName(String pname) throws PersistenceException {
        Computer returnComputer = null;
        String query = SELECT_JOIN_COMPUTER + " WHERE computer.name=?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pname);
            ResultSet results;
            results = ps.executeQuery();
            returnComputer = PersistenceMapper.mapResultToComputer(results);
            ;
            results.close();
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : getByName(String) catched SQLException and throwed PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        return returnComputer;
    }

    /**
     * A method that allows to create a computer in the table using a computer
     * entity.
     *
     * @param pcomputer the computer instance that we want to add to the table
     * @return
     */
    @Override
    public Computer create(Computer toCreate) {
        jdbcTemplate.update(CREATE_COMPUTER, new Object[] {toCreate.getName(),
            toCreate.getIntroduced(), toCreate.getDiscontinued(), 
            toCreate.getManufacturer().getId()});           
        return toCreate;
    }

    /**
     * A method that allows to update an existing computer in our database.
     *
     * @param pcomputer the new version of a computer that we want to update
     * @return
     */

    @Override
    public Computer update(Computer toUpdate) {
        jdbcTemplate.update(UPDATE_COMPUTER, new Object[] {toUpdate.getName(),
                 toUpdate.getIntroduced(), toUpdate.getDiscontinued(),
                 toUpdate.getManufacturer().getId(), toUpdate.getId()});
        return toUpdate;
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param pcomputer The computer entry that we want to delete from our
     *            table. The only used parameter from it is its id.
     */
    @Override
    public void delete(Computer toDelete) {
            jdbcTemplate.update(DELETE_COMPUTER, 
                    new Object[] {toDelete.getId()});
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param id the id of the entry that we want to delete from our table
     */
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE_COMPUTER,
                new Object[] {id});
    }

    /**
     * 
     * @param
     */
    @Override
    public void deleteFromCompany(Long companyId, Connection connection) {
            jdbcTemplate.update(DELETE_FROM_COMPANY_COMPUTER, 
                    new Object[] {companyId});
    }

}