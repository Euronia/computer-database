package com.excilys.formation.tj.cli;

import java.util.List;
import java.util.Scanner;

import com.excilys.formation.tj.dao.DAOCompany;
import com.excilys.formation.tj.entities.Company;
import com.excilys.formation.tj.entities.Computer;

/**
 * This menu allows the user to do an action on the company table.
 * Currently only allows to list companies.
 * @author Euronia
 * @version 1.0
 */

public class MenuCompany implements IMenu{

	MainMenu main = new MainMenu();
	Scanner scanner = main.getScanner();
	
	@Override
	public void startMenu() {
		System.out.println("/// Menu compagnie ///"
				+ " \n Souhaitez vous : "
				+ "\n 1- Lister les compagnies "
				+ "\n 2- Retourner au menu principal"
				+ "\n 3- Quitter l'application");
		while (!scanner.hasNextInt()) scanner.next();
		int choice = scanner.nextInt();
		DAOCompany daoCompany = new DAOCompany();
		switch (choice)
		{
		case 1:
			List<Company> companies = daoCompany.getList();
			for (Company cp : companies)
			{
				System.out.println(cp.toString() + "\n");
			}
			startMenu();
			break;
		case 2:
			main.startMenu();
			break;
		case 3:
			break;
		}
	}

	@Override
	public void exitMenu() {

		
	}

}
