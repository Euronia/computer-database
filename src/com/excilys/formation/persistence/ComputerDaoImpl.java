/**
 * 
 */
package com.excilys.formation.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.entity.*;
import com.excilys.formation.persistence.exception.PersistenceException;

/**
 * 
 * @author Euronia
 * @version 1.0
 */

public class ComputerDaoImpl implements ComputerDao {
	
	////////// Attributes //////////
	
	ConnectionProvider jdbcUtil ;
	
	////////// Constructors //////////

	public ComputerDaoImpl () 
	{
		this.jdbcUtil = new ConnectionProvider();
	}
	
	////////// Getters and Setters //////////
	
	public ConnectionProvider getJdbcUtil() {
		return jdbcUtil;
	}

	public void setJdbcUtil(ConnectionProvider jdbcUtil) {
		this.jdbcUtil = jdbcUtil;
	}
	
	////////// Methods //////////
	
	
	/**
	 * A method that returns the list of the DAO Object (here Computer)
	 * using a ResultSet.
	 * @param presults the result of a query on the computer table (no check if on other table)
	 * @return An ArrayList of the different computers that were found for this query.
	 */
	public List<Computer> getListFromResults (ResultSet presults)
	{
		if (presults == null)
		{
			// Plutot retourner une liste vide ?
			return null;
		}
		List<Computer> returnList = new ArrayList<Computer>();
		try {
			while (presults.next())
			{
				int id = presults.getInt("id");
				String name = presults.getString("Name");
				LocalDate introDate = (LocalDate) presults.getObject("introduced");
				LocalDate disconDate = (LocalDate) presults.getObject("discontinued");
				int companyID = presults.getInt("company_id");
				
				//returnList.add(Computer.ComputerBuilder(name,companyID).build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * A simple query select * on the computer table.
	 * This method uses getListFromResults() to turn the ResultSet into a List
	 * @return a List of all the entries in the computer table
	 */	
	@Override
	public List<Computer> getList() 
	{
		jdbcUtil.openConnection();
		String query = "SELECT * FROM computer;";
		ResultSet results;
		List<Computer> returnList = new ArrayList<Computer>();
		
		try
		{
			Statement stmt = jdbcUtil.getConnection().createStatement();
			results = stmt.executeQuery(query);
			returnList = getListFromResults(results);
		}
		catch (Exception e)
		{
			System.out.println("La requete GetList de DAOComputer a échouée");
		}
		
		jdbcUtil.closeConnection();
		return returnList;
	}
	
	/**
	 * A method that gets an entry in the computer table using its id
	 * @param pid The id of the wanted entry. If it's not a valid id a null computer with an id of 0 will be returned.
	 * @return the wanted entry on our computer table, can return a null computer for a non valid requested id.
	 */

	public Computer getByID(int pid) 
	{
		jdbcUtil.openConnection();
		String query = "Select * FROM computer WHERE id=?";
		Computer returnComputer = null;	//Initialization in case of Exception
		
		try 
		{
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
			ps.setInt(1, pid);
			ResultSet results;
			results = ps.executeQuery();
			List<Computer> resultList = getListFromResults(results);
			if (!resultList.isEmpty())
				returnComputer = resultList.get(0);
		}
		catch (Exception e)
		{
			System.out.println("La requete getById de DAOComputer a échouée");	
		}
		jdbcUtil.closeConnection();
		return returnComputer;
	}
	
	@Override
	public Computer getByName(String pname) throws PersistenceException {
		jdbcUtil.openConnection();
		String query = "Select * FROM computer WHERE name=?";
		Computer returnComputer = null;
		
		try 
		{
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
			ps.setString(1, pname);
			ResultSet results;
			results = ps.executeQuery();
			List<Computer> resultList = getListFromResults(results);
			if (!resultList.isEmpty())
				returnComputer = resultList.get(0);
		}
		catch (Exception e)
		{
			System.out.println("La requete getByName de DAOComputer a échouée");	
		}
		jdbcUtil.closeConnection();
		return returnComputer;
	}
	
	
	/**
	 * A method that allows to create a computer in the table using a computer entity?object?
	 * @param pcomputer the computer instance that we want to add to the table
	 * @return 
	 */
	@Override
	public Computer create(Computer pToCreate)
	{
		jdbcUtil.openConnection();
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
			
			ps.setString(1, pToCreate.getName());
			ps.setObject(2, pToCreate.getIntroduced());
			ps.setObject(3, pToCreate.getDiscontinued());
			ps.setInt(4, pToCreate.getManufacturer().getId());
			ps.executeUpdate();			
		}
		catch (Exception e)
		{
			
		}
		
		jdbcUtil.closeConnection();
		return pToCreate;
	}
	
	/**
	 * A method that allows to update an existing computer in our database
	 * @param pcomputer the new version of a computer that we want to update
	 * @return 
	 */
	
	@Override
	public Computer update(Computer pToUpdate) 
	{
		jdbcUtil.openConnection();
		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ;";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
			
			ps.setString(1, pToUpdate.getName());
			ps.setObject(2, pToUpdate.getIntroduced());
			ps.setObject(3, pToUpdate.getDiscontinued());
			ps.setInt(4, pToUpdate.getManufacturer().getId());
			ps.setInt(5, pToUpdate.getId());
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			
		}
		jdbcUtil.closeConnection();
		return pToUpdate;
	}
	
	/**
	 * A method that deletes an entry of the computer table.
	 * @param pcomputer The computer entry that we want to delete from our table.
	 * 			The only used parameter from it is its id.
	 */
	@Override
	public void delete (Computer pcomputer)
	{
		jdbcUtil.openConnection();
		String query = "DELETE computer WHERE id = ?";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
		
			ps.setInt(1, pcomputer.getId());
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			
		}
		jdbcUtil.closeConnection();
	}
	
	/**
	 * A method that deletes an entry of the computer table.
	 * @param pidComputer the id of the entry that we want to delete from our table
	 */
	
	public void delete(int pid) 
	{
		jdbcUtil.openConnection();
		String query = "DELETE FROM computer WHERE id = ?";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
		
			ps.setInt(1, pid);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			
		}
		jdbcUtil.closeConnection();
	}






				
		
}



