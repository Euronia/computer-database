package com.excilys.formation.tj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Euronia
 * @version 1.0
 */

public class JDBCUtil {

	////////// Parameters //////////
	
	private Connection connection;
	private String url;
	
	////////// Constructors //////////
	
	public JDBCUtil () 
	{
		this.url = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	}
	
	////////// Getters and Setters //////////
	

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	////////// Methods //////////

	public void openConnection () 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(url,"admincdb","qwerty1234"); 
		}
		catch (ClassNotFoundException e)
		{	
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnection  ()
	{
		// Verifier si la connection est ouverte ? 
		try 
		{
			this.connection.close();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


