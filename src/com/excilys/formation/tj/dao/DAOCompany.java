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

public class DAOCompany {
	
	////////// Attributes //////////
	
	JDBCUtil jdbcUtil ;
	
	////////// Constructors //////////

	public DAOCompany () 
	{
		this.jdbcUtil = new JDBCUtil();
	}
	
	////////// Methods //////////
	
	public List<Company> getList() 
	{
		jdbcUtil.openConnection();
		String query = "SELECT * FROM computer;";
		ResultSet results;
		List<Company> returnList = new ArrayList<Company>();
		
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
	
	public List<Company> getListFromResults (ResultSet presults)
	{
		if (presults == null)
		{
			// Plutot retourner une liste vide ?
			return null;
		}
		List<Company> returnList = new ArrayList<Company>();
		try {
			while (presults.next())
			{
				int id = presults.getInt("id");
				String name = presults.getString("Name");
				returnList.add(new Company(id,name));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
}
	