package com.excilys.formation.tj.cli;

import java.util.Scanner;

/**
 * The first menu the user will see when starting the program.
 * @author Euronia
 * @version 1.0
 */

public class MainMenu implements IMenu{
	
	public static Scanner scanner = new Scanner(System.in);

	@Override
	public void startMenu() {
		IMenu nextMenu;
		System.out.println("/// Menu Principal ///"
				+ " \n Souhaitez vous acceder à : "
				+ "\n 1- Le menu des ordinateurs "
				+ "\n 2- Le menu des companies"
				+ "\n 3- Quitter l'application");
		while (!scanner.hasNextInt()) scanner.next();
		int choice = scanner.nextInt();
		switch (choice)
		{
		case 1:
			nextMenu = new MenuComputer();
			nextMenu.startMenu();
			break;
		case 2: 
			nextMenu = new MenuCompany();
			nextMenu.startMenu();
			break;
		case 3:
			exitMenu();
			break;
		default:
			System.out.println("Mauvaise entrée");
			break;
		}	
		
		
	}
	
	public static Scanner getScanner()
	{
		return scanner;
	}
	
	@Override
	public void exitMenu() {
		
	}
}
