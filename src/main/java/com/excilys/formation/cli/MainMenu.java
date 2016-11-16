package com.excilys.formation.cli;

import java.util.Scanner;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * The first menu the user will see when starting the program.
 * 
 * @author Euronia
 * @version 1.0
 */

public class MainMenu implements BaseMenu {

    public static Scanner scanner = new Scanner(System.in);
    private static final Logger logger = (Logger) LoggerFactory.getLogger(MainMenu.class);

    @Override
    public void startMenu() {
        BaseMenu nextMenu;
        System.out.println("/// Menu Principal ///" + " \n Souhaitez vous acceder à : "
                + "\n 1- Le menu des ordinateurs " + "\n 2- Le menu des companies" + "\n 3- Quitter l'application");
        while (!scanner.hasNextInt())
            scanner.next();
        int choice = scanner.nextInt();
        switch (choice) {
        case 1:
            logger.info("Main menu -> Computer menu");
            nextMenu = new ComputerMenu();
            nextMenu.startMenu();
            break;
        case 2:
            nextMenu = new CompanyMenu();
            nextMenu.startMenu();
            break;
        case 3:
            exitMenu();
            break;
        default:
            System.out.println("Mauvaise entrée");
            startMenu();
            break;
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    @Override
    public void exitMenu() {
    }
}
