package com.excilys.formation.cli;

/**
 * @author Euronia
 * @version 1.0
 */
public class Main {

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
