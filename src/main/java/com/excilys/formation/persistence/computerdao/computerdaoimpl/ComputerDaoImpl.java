package com.excilys.formation.persistence.computerdao.computerdaoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

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

public class ComputerDaoImpl implements ComputerDao {

    ////////// Attributes //////////

    private static HikariConnectionProvider connectionProvider;
    private static Logger logger;
    private static final ComputerDaoImpl COMPUTER_DAO_INSTANCE;
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

    public HikariConnectionProvider getConnectionProvider() {
        return connectionProvider;
    }

    ////////// Methods //////////

    /**
     * A simple query select * on the computer table. This method uses
     * getListFromResults() to turn the ResultSet into a List
     *
     * @return a List of all the entries in the computer table
     */
    @Override
    public Page<Computer> getPage(Page<Computer> page) throws PersistenceException {
        List<Computer> computers = new ArrayList<Computer>();
        String query = SELECT_JOIN_COMPUTER + " LIMIT ? OFFSET ?";
        try (Connection connection = connectionProvider.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, page.getElementsByPage());
            preparedStatement.setInt(2, (page.getCurrentPage() - 1) * page.getElementsByPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            computers = PersistenceMapper.mapResultsToComputerList(resultSet);
            page.setElements(computers);
            page.setTotalElements(count());
            resultSet.close();
        } catch (SQLException e) {
            logger.error(
                    "ComputerDaoImpl : getPage(Page<Computer>) catched SQLException and throwed PersistenceException ");
            throw new PersistenceException("La requete getPage de DAOComputer a échouée", e);
        }
        return page;
    }

    public Page<Computer> getAllFilter(Page<Computer> page, String filterConstraint) throws PersistenceException {
        List<Computer> computers = new ArrayList<Computer>();
        String filter = "%" + filterConstraint + "%";
        System.out.println("Filter :" + filter);
        String query = SELECT_JOIN_COMPUTER + " WHERE computer.name LIKE ? OR company.name LIKE ? LIMIT ? OFFSET ? ";
        try (Connection connection = connectionProvider.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, filter);
            preparedStatement.setString(2, filter);
            preparedStatement.setInt(3, page.getElementsByPage());
            preparedStatement.setInt(4, (page.getCurrentPage() - 1) * page.getElementsByPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            computers = PersistenceMapper.mapResultsToComputerList(resultSet);
            page.setElements(computers);
            page.setTotalElements(count(filter));
            resultSet.close();
        } catch (SQLException e) {
            logger.error(
                    "ComputerDaoImpl : getAllFilter(Page<Computer>, String) catched SQLException and throwed PersistenceException ");
            throw new PersistenceException("La requete getAllFilter de DAOComputer a échouée", e);
        }
        return page;
    }

    private int count() {
        int total = 0;
        try (Connection connection = connectionProvider.getConnection();
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

    private int count(String filter) {
        int total = 0;
        try (Connection connection = connectionProvider.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_FILTERED)) {

            preparedStatement.setString(1, filter);
            preparedStatement.setString(2, filter);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            total = resultSet.getInt("total");
            resultSet.close();
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : count(String) catched SQLException");
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
        try (Connection connection = connectionProvider.getConnection();
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
        try (Connection connection = connectionProvider.getConnection();
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
        try (Connection connection = connectionProvider.getConnection();
                PreparedStatement ps = connection.prepareStatement(CREATE_COMPUTER)) {
            ps.setString(1, toCreate.getName());
            ps.setObject(2, toCreate.getIntroduced());
            ps.setObject(3, toCreate.getDiscontinued());
            ps.setLong(4, toCreate.getManufacturer().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : count(String) catched SQLException and throwed PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
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
        try (Connection connection = connectionProvider.getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_COMPUTER)) {
            ps.setString(1, toUpdate.getName());
            ps.setObject(2, toUpdate.getIntroduced());
            ps.setObject(3, toUpdate.getDiscontinued());
            ps.setLong(4, toUpdate.getManufacturer().getId());
            ps.setLong(5, toUpdate.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : update(Computer) catched SQLException and throwed PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        return toUpdate;
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param pcomputer The computer entry that we want to delete from our
     *            table. The only used parameter from it is its id.
     */
    @Override
    public void delete(Computer pcomputer) {
        try (Connection connection = connectionProvider.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_COMPUTER)) {
            ps.setLong(1, pcomputer.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("ComputerDaoImpl : delete(Computer) catched SQLException and throwed PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param id the id of the entry that we want to delete from our table
     */
    @Override
    public void delete(long id) {
        try (Connection connection = connectionProvider.getConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_COMPUTER)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("ComputerDaoImpl : delete(long) catched SQLException and throwed PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
    }

    /**
     * 
     * @param
     */
    @Override
    public void deleteFromCompany(Long companyId, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_FROM_COMPANY_COMPUTER)) {
            ps.setLong(1, companyId);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("ComputerDaoImpl : deleteFromCompany() catched SQLException", e);
        }
    }

}