package com.excilys.formation.cli;

import java.util.Scanner;

import com.excilys.formation.config.SpringCliConfig;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The first menu the user will see when starting the program.
 * 
 * @author Euronia
 * @version 1.0
 */

public class MainMenu implements BaseMenu {

    public static Scanner scanner = new Scanner(System.in);
    private static final Logger logger = (Logger) LoggerFactory.getLogger(MainMenu.class);

    private CompanyMenu companyMenu;

    private ComputerMenu computerMenu;

    public MainMenu() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringCliConfig.class);
        computerMenu = context.getBean(ComputerMenu.class);
        companyMenu = context.getBean(CompanyMenu.class);
    }


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
            computerMenu.startMenu();
            startMenu();
            break;
        case 2:
            companyMenu.startMenu();
            startMenu();
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
