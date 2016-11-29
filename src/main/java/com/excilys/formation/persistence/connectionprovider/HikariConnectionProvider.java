package com.excilys.formation.persistence.connectionprovider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.util.PropertyReader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionProvider {

    ////////// Parameters //////////

    private static final HikariConnectionProvider CONNECTION_PROVIDER_INSTANCE;
    private static final String PROPERTIES_ADRESS = "connection.properties";
    private static Logger logger;
    private DataSource datasource;
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    public HikariConnectionProvider(DataSource ds) {
        this.datasource = ds;
    }

    static {

        Properties properties = PropertyReader.getInstance().readProperties(PROPERTIES_ADRESS);
        if (properties.isEmpty()) {
            throw new RuntimeException("Invalid Properties for Hikari connection");
        }
        HikariConfig config = new HikariConfig(properties);
        HikariDataSource ds = new HikariDataSource(config);
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");

        CONNECTION_PROVIDER_INSTANCE = new HikariConnectionProvider(ds);
    }

    public static HikariConnectionProvider getInstance() {
        return CONNECTION_PROVIDER_INSTANCE;
    }

    public void closeConnection() {
        try {
            datasource.getConnection().close();
        } catch (SQLException e) {
            logger.error("HikariConnectionProvider : closeConnection() catched SQLException ");
            logger.error(e.getStackTrace().toString());
        }
    }

    public void openConnection() {
        try {
            datasource.getConnection();
        } catch (SQLException e) {
            logger.error("HikariConnectionProvider : openConnection() catched SQLException ");
            logger.error(e.getStackTrace().toString());
        }
    }

    /**
     * Returns the connection located in the ThreadLocal used for the
     * transaction.
     * 
     * @return null if the connection wasn't initialized otherwise return the
     *         connection located in the ThreadLocal.
     */
    public Connection getTransactionConnection() {
        return threadLocal.get();
    }

    public void closeTransactionConnection() {
        try {
            threadLocal.get().close();
        } catch (SQLException e) {
            logger.error("HikariConnectionProvider : closeTransactionConenction() catched SQLException", e);
        }
        threadLocal.set(null);
    }

    public void beginTransaction() {
        try {
            threadLocal.set(datasource.getConnection());
            threadLocal.get().setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("HikariConnectionProvider : beginTransaction() catched SQLException ", e);
        }
    }

    public void rollbackTransaction() {
        try {
            threadLocal.get().rollback();
            threadLocal.get().setAutoCommit(true);
            closeTransactionConnection();
        } catch (SQLException e) {
            logger.error("HikariConnectionProvider : rollbackTransaction() catched SQLException", e);
        }

    }

    public void commitTransaction() {
        try {
            threadLocal.get().commit();
            threadLocal.get().setAutoCommit(true);
            closeTransactionConnection();
        } catch (SQLException e) {
            rollbackTransaction();
            logger.error("Default error", e);
        }

    }
}
