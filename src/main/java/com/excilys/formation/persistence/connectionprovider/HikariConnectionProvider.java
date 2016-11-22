package com.excilys.formation.persistence.connectionprovider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.formation.util.PropertyReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionProvider implements ConnectionProvider {

    ////////// Parameters //////////
    
    private static final HikariConnectionProvider CONNECTIONPROVIDER_INSTANCE;
    private static final String PROPERTIES_ADRESS = "hikariConnection.properties";
    private HikariDataSource datasource ;
    
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }
    
    public HikariConnectionProvider (HikariDataSource ds)
    {
        this.datasource = ds;
    }
    
    static{
        
        Properties properties = new Properties();
        properties = PropertyReader.getInstance().readProperties(PROPERTIES_ADRESS);
        if (properties.isEmpty())
        {
            throw new RuntimeException("Invalid Properties for Hikari connection");
        }
        HikariConfig config = new HikariConfig(properties);
        HikariDataSource ds = new HikariDataSource(config);
        
        CONNECTIONPROVIDER_INSTANCE = new HikariConnectionProvider(ds);
    }

    public static ConnectionProvider getInstance()
    {
        return CONNECTIONPROVIDER_INSTANCE;
    }
    
    public void closeConnection() {
        try {
            datasource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
