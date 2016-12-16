/**
 * 
 */
package com.excilys.formation.cli;

import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerDaoImpl;

/**
 * @author Euronia
 * @version 1.0
 */
public class Main {

    public static ComputerDaoImpl daoComputer = new ComputerDaoImpl();

    /**
     * The main function of our program. Creates a mainMenu and start its menu.
     * 
     * @param args
     */
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.startMenu();
    }
}
