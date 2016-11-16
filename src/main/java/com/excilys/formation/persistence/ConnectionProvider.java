package com.excilys.formation.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.formation.util.PropertyReader;

/**
 * Class that creates the Database connection It uses the Singleton Pattern.
 * 
 * @author Euronia
 * @version 1.0
 */

public class ConnectionProvider {

    ////////// Parameters //////////

    private static ConnectionProvider connectionProvider;
    private Connection connection;
    private Properties properties;
    private static final String PROPERTIES_ADRESS = "connection.properties";

    public static ConnectionProvider getInstance() {
        if (connectionProvider == null) {
            connectionProvider = new ConnectionProvider();
        }
        return connectionProvider;

    }

    ////////// Constructors //////////

    public ConnectionProvider() {
        ClassLoader classLoader = getClass().getClassLoader();
        properties = PropertyReader.readProperties(classLoader.getResourceAsStream(PROPERTIES_ADRESS));;
    }

    ////////// Getters and Setters //////////

    public Connection getConnection() {
        return connection;
    }

    ////////// Methods //////////

    public void openConnection() {
        try {
            Class.forName(properties.getProperty("driver"));
            this.connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("login"), properties.getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                try {
                    this.connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
