package com.excilys.formation.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Euronia
 * @version 1.0
 */
@Component
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
