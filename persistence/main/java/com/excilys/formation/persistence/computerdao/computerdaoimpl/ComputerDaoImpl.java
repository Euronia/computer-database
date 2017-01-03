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

import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.CustomResultSetExtractor;
import com.excilys.formation.mapper.CustomRowMapper;
import com.excilys.formation.mapper.PersistenceMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.computerdao.ComputerDao;

/**
 * @author Euronia
 */


public class ComputerDaoImpl implements ComputerDao {

    ////////// Attributes //////////

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static Logger logger;
    private static ComputerDaoImpl COMPUTER_DAO_INSTANCE;
    public static final String SELECT_JOIN_COMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id";
    public static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
    public static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ;";
    public static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
    public static final String COUNT_ALL = "SELECT COUNT(*) as total FROM computer";
    public static final String COUNT_ALL_FILTERED = "SELECT COUNT(*) as total FROM ( SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ? OR company.name LIKE ? ) AS derivedTable";
    public static final String DELETE_FROM_COMPANY_COMPUTER = "DELETE FROM computer WHERE computer.company_id = ?";
    private static final ResultSetExtractor<Computer> EXTRACTOR_COMPUTER = CustomResultSetExtractor.singletonExtractor(CustomRowMapper.MAPPER_COMPUTER);

    static {
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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ////////// Methods //////////

    public Page<Computer> getPage(Page<Computer> page, PageConstraints constraints) throws PersistenceException {
        List<Computer> computers = new ArrayList<Computer>();
        ArrayList<Object> statementFields = new ArrayList<Object>();
        String query = SELECT_JOIN_COMPUTER + getConstraintsString(constraints) + " LIMIT ? OFFSET ? ";
        if (constraints.getConstraint().containsKey("filter") && constraints.getConstraint().containsKey("orderBy")) {
            statementFields.add(constraints.getConstraint().get("filter"));
            statementFields.add(constraints.getConstraint().get("filter"));
            statementFields.add(constraints.getConstraint().get("orderBy"));
            statementFields.add(page.getElementsByPage());
            statementFields.add((page.getCurrentPage() - 1) * page.getElementsByPage());
        } else if (constraints.getConstraint().containsKey("orderBy")) {
            statementFields.add(constraints.getConstraint().get("orderBy"));
            statementFields.add(page.getElementsByPage());
            statementFields.add((page.getCurrentPage() - 1) * page.getElementsByPage());
        } else if (constraints.getConstraint().containsKey("filter")) {
            statementFields.add(constraints.getConstraint().get("filter"));
            statementFields.add(constraints.getConstraint().get("filter"));
            statementFields.add(page.getElementsByPage());
            statementFields.add((page.getCurrentPage() - 1) * page.getElementsByPage());
        } else {
            statementFields.add(page.getElementsByPage());
            statementFields.add((page.getCurrentPage() - 1) * page.getElementsByPage());
        }
        computers = jdbcTemplate.query(query,
                statementFields.toArray(),
                CustomRowMapper.MAPPER_COMPUTER);
        page.setElements(computers);
        page.setTotalElements(count(constraints));
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

    private int count(PageConstraints constraints) {
        int total = 0;
        String newQuery = "SELECT COUNT(*) as total FROM ( " + SELECT_JOIN_COMPUTER + getConstraintsString(constraints)
                + " ) AS derivedTable";
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
     * computer for a non valid requested id.
     */
    public Computer getById(long id) {
        return jdbcTemplate.query(SELECT_JOIN_COMPUTER + " WHERE computer.id=?", new Object[]{id}, EXTRACTOR_COMPUTER);
    }

    @Override
    public Computer getByName(String name) throws PersistenceException {
        return jdbcTemplate.query(SELECT_JOIN_COMPUTER + " WHERE computer.name=?", new Object[]{name}, EXTRACTOR_COMPUTER);
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
        jdbcTemplate.update(CREATE_COMPUTER, new Object[]{toCreate.getName(), toCreate.getIntroduced(),
                toCreate.getDiscontinued(), toCreate.getManufacturer().getId()});
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
        jdbcTemplate.update(UPDATE_COMPUTER, new Object[]{toUpdate.getName(), toUpdate.getIntroduced(),
                toUpdate.getDiscontinued(), toUpdate.getManufacturer().getId(), toUpdate.getId()});
        return toUpdate;
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param pcomputer The computer entry that we want to delete from our
     *                  table. The only used param
     *                  eter from it is its id.
     */
    @Override
    public void delete(Computer toDelete) {
        jdbcTemplate.update(DELETE_COMPUTER, new Object[]{toDelete.getId()});
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param id the id of the entry that we want to delete from our table
     */
    @Override
    public void delete(long id) {
        jdbcTemplate.update(DELETE_COMPUTER, new Object[]{id});
    }

    /**
     * @param
     */
    @Override
    public void deleteFromCompany(Long companyId) {
        jdbcTemplate.update(DELETE_FROM_COMPANY_COMPUTER, new Object[]{companyId});
    }

}