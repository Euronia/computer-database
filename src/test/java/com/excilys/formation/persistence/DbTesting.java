package com.excilys.formation.persistence;

import java.io.FileInputStream;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 * Initiate and populate the database for persistence tests
 * 
 * @author Euronia
 *
 */

public class DbTesting {

    /**
     * returns a DataSet from an existing XML File representing our database
     * 
     * @return a database in the DataSet format
     * @throws Exception
     */
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/computer-database-test.xml"));
    }

    @BeforeClass
    public static void createSchema() throws Exception {
        // RunScript.execute(connectionTest.getUrl(), connectionTest.getUser(),
        // connectionTest.getPwd(), "src/test/config/db-test/4-TESTSCHEMA.sql",
        // Charset.forName("UTF8"), false);
    }

    public void cleanAndRepopulate(IDataSet dataSet) throws Exception {
        /*
         * IDatabaseTester databaseTester = new
         * JdbcDatabaseTester(connectionTest.getDriver(),
         * connectionTest.getUrl(), connectionTest.getUser(),
         * connectionTest.getPwd());
         * databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
         * databaseTester.setDataSet(dataSet); databaseTester.onSetup();
         */
    }

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = getDataSet();
        cleanAndRepopulate(dataSet);
    }
}
