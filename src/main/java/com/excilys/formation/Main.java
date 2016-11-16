/**
 * 
 */
package com.excilys.formation;

import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.persistence.*;

/**
 * @author Euronia
 * @version 1.0
 */
public class Main {

    public static ComputerDaoImpl daoComputer = new ComputerDaoImpl();

    /**
     * The main function of our program. Creates a mainMenu and start its menu.
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
     /*   ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
        connectionProvider.openConnection();
        IDatabaseConnection connection = null;
        try {
            connection = new DatabaseConnection(connectionProvider.getConnection());
        } catch (DatabaseUnitException e1) {
            e1.printStackTrace();
        }
        IDataSet fullDataSet = null;
        try {
            String[] strings = new String[2];
            strings[0] = "computer";
            strings[1] = "company"; 
            try {
                fullDataSet = connection.createDataSet(strings);
            } catch (DataSetException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
        } catch (DataSetException | IOException e) {
            e.printStackTrace();
        }
        connectionProvider.closeConnection(); */
        MainMenu mainMenu = new MainMenu();
        mainMenu.startMenu();
    }
}
