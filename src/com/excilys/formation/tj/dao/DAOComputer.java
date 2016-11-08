/**
 * 
 */
package com.excilys.formation.tj.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.tj.entities.*;

/**
 * 
 * @author Euronia
 * @version 1.0
 */

public class DAOComputer {
	
	////////// Attributes //////////
	
	JDBCUtil jdbcUtil ;
	
	////////// Constructors //////////

	public DAOComputer () 
	{
		this.jdbcUtil = new JDBCUtil();
	}
	
	////////// Getters and Setters //////////
	
	public JDBCUtil getJdbcUtil() {
		return jdbcUtil;
	}

	public void setJdbcUtil(JDBCUtil jdbcUtil) {
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
				Date introDate = presults.getDate("introduced");
				Date disconDate = presults.getDate("discontinued");
				int companyID = presults.getInt("company_id");
				returnList.add(new Computer(id,name,introDate,disconDate,companyID));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * A simple query select * on the computer table.
	 * This method uses getListFromResults() to turn the ResultSet into a List
	 * @return a List of all the entries in the computer table
	 */	
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
	public Computer getComputerById (int pid) 
	{
		jdbcUtil.openConnection();
		String query = "Select * FROM computer WHERE id=?";
		Computer returnComputer = new Computer();
		
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
			System.out.println("La requete getComputerById de DAOComputer a échouée");	
		}
		jdbcUtil.closeConnection();
		return returnComputer;
	}
		
	
	/**
	 * A method that allows to create a computer in the table using a computer entity?object?
	 * @param pcomputer the computer instance that we want to add to the table
	 */
	public void createComputer (Computer pcomputer)
	{
		jdbcUtil.openConnection();
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
			
			ps.setString(1, pcomputer.getName());
			ps.setDate(2, pcomputer.getIntroduced());
			ps.setDate(3, pcomputer.getDiscontinued());
			ps.setInt(4, pcomputer.getCompanyID());
			ps.executeUpdate();
			
		}
		catch (Exception e)
		{
			
		}
		
		jdbcUtil.closeConnection();
	}
	
	/**
	 * A method that allows to update an existing computer in our database
	 * @param pcomputer the new version of a computer that we want to update
	 */
	public void updateComputer (Computer pcomputer)
	{
		jdbcUtil.openConnection();
		String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ;";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
			
			ps.setString(1, pcomputer.getName());
			ps.setDate(2, pcomputer.getIntroduced());
			ps.setDate(3, pcomputer.getDiscontinued());
			ps.setInt(4, pcomputer.getCompanyID());
			ps.setInt(5, pcomputer.getId());
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			
		}
		jdbcUtil.closeConnection();
	}
	
	/**
	 * A method that deletes an entry of the computer table.
	 * @param pcomputer The computer entry that we want to delete from our table.
	 * 			The only used parameter from it is its id.
	 */
	public void deleteComputer (Computer pcomputer)
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
	public void deleteComputer (int pidComputer)
	{
		jdbcUtil.openConnection();
		String query = "DELETE FROM computer WHERE id = ?";
		try {
			PreparedStatement ps = jdbcUtil.getConnection().prepareStatement(query);
		
			ps.setInt(1, pidComputer);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			
		}
		jdbcUtil.closeConnection();
	}
	
				
		
}



