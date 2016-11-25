package com.excilys.formation.persistence;

import java.io.FileOutputStream;
import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import com.excilys.formation.persistence.connectionprovider.HikariConnectionProvider;

/** 
 * Allows to export an existing database to an XML file
 * @author Euronia
 */

public class DbToXml {
    public static void main(String[] args) throws Exception {
       //Opens the connection to the database
       Connection connection = HikariConnectionProvider.getInstance().getConnection();
       IDatabaseConnection dbConnection = new DatabaseConnection(connection);
       // Creates a DataSet from our database
       IDataSet fullDataSet = dbConnection.createDataSet();
       // Writes the DataSet as an XML
       FlatXmlDataSet.write(fullDataSet, new FileOutputStream("src/test/resources/computer-database.xml"));
       connection.close();
    }
}
