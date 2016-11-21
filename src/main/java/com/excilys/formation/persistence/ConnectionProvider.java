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

    private static final ConnectionProvider CONNECTIONPROVIDER_INSTANCE;
    private Connection connection;
    private static final String PROPERTIES_ADRESS = "connection.properties";
    private String url;
    private String driver;
    private String user;
    private String pwd;
    
    ////////// Constructors //////////

    public ConnectionProvider(String url, String driver, String user, String pwd) {
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.pwd = pwd;
    }

    static {
        String nestedUrl;
        String nestedDriver;
        String nestedUser;
        String nestedPwd;
        Properties properties;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        properties = PropertyReader.readProperties(classLoader.getResourceAsStream(PROPERTIES_ADRESS));
        if (properties == null)
        {
            throw new RuntimeException("Empty connection properties or invalid adress");
        }
        nestedUrl = properties.getProperty("url");
        nestedDriver = properties.getProperty("driver");
        nestedUser = properties.getProperty("user");
        nestedPwd = properties.getProperty("pwd");
        try {
            Class.forName(nestedDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        CONNECTIONPROVIDER_INSTANCE = new ConnectionProvider(nestedUrl,nestedDriver,nestedUser,nestedPwd);
    }
    
    public static ConnectionProvider getInstance() {
        return CONNECTIONPROVIDER_INSTANCE;
    }


    ////////// Getters and Setters //////////

    public Connection getConnection() throws SQLException {
        if (connection ==null){
            connection = DriverManager.getConnection(url,user,pwd);
        }
        return connection;
    }
    
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getDriver() {
        return driver;
    }


    public void setDriver(String driver) {
        this.driver = driver;
    }


    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }


    public String getPwd() {
        return pwd;
    }


    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    ////////// Methods //////////

    public void openConnection() {
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url,user,pwd);
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
