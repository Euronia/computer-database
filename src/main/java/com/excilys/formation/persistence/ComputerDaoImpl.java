/**
 *
 */
package com.excilys.formation.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.exception.PersistenceException;
import com.excilys.formation.persistence.mapper.PersistenceMapper;

/**
 * @author Euronia
 * @version 1.0
 */

public class ComputerDaoImpl implements ComputerDao {

    ////////// Attributes //////////

    ConnectionProvider connectionProvider;
    private static ComputerDaoImpl computerDaoImpl = null;
    public static final String SELECT_JOIN_COMPUTER ="SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as companyId, company.name as companyName FROM computer LEFT JOIN company ON computer.company_id=company.id";
    public static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
    public static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ;";
    public static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
    public static final String COUNT_ALL = "SELECT COUNT(*) as total FROM computer";
    
    ////////// Constructors //////////
    /**
     * Basic Constructor.
     */
    public ComputerDaoImpl() {
        this.connectionProvider = new ConnectionProvider();
    }

    public static ComputerDaoImpl getInstance() {
        if (computerDaoImpl == null) {
            computerDaoImpl = new ComputerDaoImpl();
        }
        return computerDaoImpl;
    }
    ////////// Getters and Setters //////////

    public ConnectionProvider getJdbcUtil() {
        return connectionProvider;
    }

    public void setJdbcUtil(ConnectionProvider jdbcUtil) {
        this.connectionProvider = jdbcUtil;
    }

    ////////// Methods //////////

    /**
     * A simple query select * on the computer table. This method uses
     * getListFromResults() to turn the ResultSet into a List
     *
     * @return a List of all the entries in the computer table
     */
    @Override
    public Page<Computer> getPage(Page<Computer> pPage) throws PersistenceException {
        connectionProvider.openConnection();
        List<Computer> computers = new ArrayList<Computer>();
        String query = SELECT_JOIN_COMPUTER + " LIMIT ? OFFSET ?";
        try {
           PreparedStatement preparedStatement = connectionProvider.getConnection().prepareStatement(query);
           preparedStatement.setInt(1, pPage.getElementsByPage());
           preparedStatement.setInt(2, (pPage.getCurrentPage() - 1) * pPage.getElementsByPage());
           ResultSet resultSet = preparedStatement.executeQuery();
           computers = PersistenceMapper.mapResultsToComputerList(resultSet);
           pPage.setElements(computers);
           pPage.setTotalElements(count());
           } catch (SQLException e) {
               e.printStackTrace();
               throw new PersistenceException("La requete getPage de DAOComputer a échouée");
           }
        connectionProvider.closeConnection();
        return pPage;
    }
    
    private int count() {
        int total = 0;
        try {
            Statement statement = connectionProvider.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_ALL);
            resultSet.next();
            total = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
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

    public Computer getById(int pid) {
        connectionProvider.openConnection();
        Computer returnComputer = null; // Initialization in case of Exception
        String query = SELECT_JOIN_COMPUTER + " WHERE computer.id=?";
        try {
            PreparedStatement ps = connectionProvider.getConnection().prepareStatement(query);
            ps.setInt(1, pid);
            ResultSet results;
            results = ps.executeQuery();
            returnComputer = PersistenceMapper.mapResultToComputer(results);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("La requete getById de DAOComputer a échouée");
        }
        connectionProvider.closeConnection();
        return returnComputer;
    }

    @Override
    public Computer getByName(String pname) throws PersistenceException {
        connectionProvider.openConnection();
        Computer returnComputer = null;
        String query = SELECT_JOIN_COMPUTER + " WHERE computer.name=?";
        try {
            PreparedStatement ps = connectionProvider.getConnection().prepareStatement(query);
            ps.setString(1, pname);
            ResultSet results;
            results = ps.executeQuery();
            returnComputer = PersistenceMapper.mapResultToComputer(results);;
        } catch (Exception e) {
            System.out.println("La requete getByName de DAOComputer a échouée");
        }
        connectionProvider.closeConnection();
        return returnComputer;
    }

    /**
     * A method that allows to create a computer in the table using a computer
     * entity?object?
     *
     * @param pcomputer the computer instance that we want to add to the table
     * @return
     */
    @Override
    public Computer create(Computer pToCreate) {
        connectionProvider.openConnection();
        try {
            PreparedStatement ps = connectionProvider.getConnection().prepareStatement(CREATE_COMPUTER);

            ps.setString(1, pToCreate.getName());
            ps.setObject(2, pToCreate.getIntroduced());
            ps.setObject(3, pToCreate.getDiscontinued());
            ps.setInt(4, pToCreate.getManufacturer().getId());
            ps.executeUpdate();
        } catch (Exception e) {

        }

        connectionProvider.closeConnection();
        return pToCreate;
    }

    /**
     * A method that allows to update an existing computer in our database.
     *
     * @param pcomputer the new version of a computer that we want to update
     * @return
     */

    @Override
    public Computer update(Computer pToUpdate) {
        connectionProvider.openConnection();
        try {
            PreparedStatement ps = connectionProvider.getConnection().prepareStatement(UPDATE_COMPUTER);

            ps.setString(1, pToUpdate.getName());
            ps.setObject(2, pToUpdate.getIntroduced());
            ps.setObject(3, pToUpdate.getDiscontinued());
            ps.setInt(4, pToUpdate.getManufacturer().getId());
            ps.setInt(5, pToUpdate.getId());
            ps.executeUpdate();
        } catch (Exception e) {

        }
        connectionProvider.closeConnection();
        return pToUpdate;
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param pcomputer The computer entry that we want to delete from our
     *            table. The only used parameter from it is its id.
     */
    @Override
    public void delete(Computer pcomputer) {
        connectionProvider.openConnection();
        try {
            PreparedStatement ps = connectionProvider.getConnection().prepareStatement(DELETE_COMPUTER);
            ps.setInt(1, pcomputer.getId());
            ps.executeUpdate();
        } catch (Exception e) {

        }
        connectionProvider.closeConnection();
    }

    /**
     * A method that deletes an entry of the computer table.
     *
     * @param pid the id of the entry that we want to delete from our
     *            table
     */

    public void delete(int pid) {
        connectionProvider.openConnection();
        try {
            PreparedStatement ps = connectionProvider.getConnection().prepareStatement(DELETE_COMPUTER);
            ps.setInt(1, pid);
            ps.executeUpdate();
        } catch (Exception e) {

        }
        connectionProvider.closeConnection();
    }
    
    

}
