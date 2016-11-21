package com.excilys.formation.persistence.connectionprovider;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {

    public static ConnectionProvider getInstance() {
        return null;
    }
    public Connection getConnection() throws SQLException;
}
