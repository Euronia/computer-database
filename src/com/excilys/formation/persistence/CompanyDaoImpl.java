/**
 * 
 */
package com.excilys.formation.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.entity.*;

/**
 * 
 * @author Euronia
 * @version 1.0
 */

public class CompanyDaoImpl {
	
	////////// Attributes //////////
	
	ConnectionProvider connectionProvider ;
	
	////////// Constructors //////////

	public CompanyDaoImpl () 
	{
		ConnectionProvider.getInstance();
	}
	
	////////// Methods //////////
	
	public List<Company> getList() 
	{
		connectionProvider.openConnection();
		String query = "SELECT * FROM computer;";
		ResultSet results;
		List<Company> returnList = new ArrayList<Company>();
		
		try
		{
			Statement stmt = connectionProvider.getConnection().createStatement();
			results = stmt.executeQuery(query);
			returnList = getListFromResults(results);
		}
		catch (Exception e)
		{
			System.out.println("La requete GetList de DAOComputer a échouée");
		}
		
		connectionProvider.closeConnection();
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
				String name = presults.getString("Name");
				Company company = new Company(name);
				company.setId(presults.getInt("id"));
				returnList.add(company);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
}
	