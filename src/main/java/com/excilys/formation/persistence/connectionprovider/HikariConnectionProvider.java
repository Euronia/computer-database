package com.excilys.formation.persistence.connectionprovider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.excilys.formation.util.PropertyReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionProvider {

    ////////// Parameters //////////
    
    private static final HikariConnectionProvider CONNECTION_PROVIDER_INSTANCE;
    private static final String PROPERTIES_ADRESS = "hikariConnection.properties";
    private DataSource datasource ;
    
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }
    
    public HikariConnectionProvider (DataSource ds) {
        this.datasource = ds;
    }
    
    static {
        
        Properties properties = PropertyReader.getInstance().readProperties(PROPERTIES_ADRESS);
        if (properties.isEmpty()) {
            throw new RuntimeException("Invalid Properties for Hikari connection");
        }
        HikariConfig config = new HikariConfig(properties);
        HikariDataSource ds = new HikariDataSource(config);
        
        CONNECTION_PROVIDER_INSTANCE = new HikariConnectionProvider(ds);
    }

    public static HikariConnectionProvider getInstance() {
        return CONNECTION_PROVIDER_INSTANCE;
    }
    
    public void closeConnection() {
        try {
            datasource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {
       try {
        datasource.getConnection();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
}
