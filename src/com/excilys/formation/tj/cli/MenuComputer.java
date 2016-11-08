package com.excilys.formation.tj.cli;

import java.util.List;
import java.util.Scanner;

import com.excilys.formation.tj.dao.DAOComputer;
import com.excilys.formation.tj.entities.Computer;

/**
 * This menu allows the user to do an action on the computer table.
 * Currently allows to list computer , see specific computer,
 * add/delete/update computer. 
 * @author Euronia
 * @version 1.0
 */

public class MenuComputer implements IMenu{

	/**
	 * @
	 */
	MainMenu main = new MainMenu();
	Scanner scanner = main.getScanner();
	
	/**
	 * Starts the Menu for the Computer part
	 * Allows an user to list/delete/create/update computers
	 */
	@Override
	public void startMenu() {
		System.out.println("/// Menu ordinateur ///"
				+ " \n Souhaitez vous : "
				+ "\n 1- Lister les ordinateurs "
				+ "\n 2- Montrer l'ordinateur possédant l'id x"
				+ "\n 3- Créer un ordinateur "
				+ "\n 4- Mettre à jour un ordinateur "
				+ "\n 5- Supprimer un ordinateur "
				+ "\n 6- Retourner au menu principal"
				+ "\n 7- Quitter l'application");
		while (!scanner.hasNextInt()) scanner.next();
		int choice = scanner.nextInt();
		DAOComputer daoComputer = new DAOComputer();
		switch (choice)
		{
		case 1:
			List<Computer> computers = daoComputer.getList();
			for (Computer cp : computers)
			{
				System.out.println(cp.toString() + "\n");
			}
			startMenu();
			break;
		case 2:
			System.out.println("Quelle est l'id de l'ordinateur auquel vous souhaitez acceder ?");
			while (!scanner.hasNextInt()) scanner.next();
			int accessID = scanner.nextInt();
			System.out.println(daoComputer.getComputerById(accessID).toString());
			startMenu();
			break;
		case 3:
			Computer computerToCreate = new Computer();
			daoComputer.createComputer(createComputerMenu(computerToCreate));	
			startMenu();
			break;
		case 4:
			System.out.println("Quelle est l'id de l'ordinateur auquel vous souhaitez acceder ?");
			while (!scanner.hasNextInt()) scanner.next();
			int updateID = scanner.nextInt();
			daoComputer.updateComputer(updateComputerMenu(daoComputer.getComputerById(updateID)));
			startMenu();
			break;
		case 5:
			System.out.println("Quelle est l'id de l'ordinateur que vous souhaitez supprimer ? (0 pour Annuler)");
			while (!scanner.hasNextInt()) scanner.next();
			int deleteID = scanner.nextInt();
			if (deleteID != 0)
				daoComputer.deleteComputer(deleteID);
			startMenu();
			break;
		case 6:
			main.startMenu();
			break;
		case 7:
			break;
		default:
			break;
		}
		
	}
	
	/**
	 * A recursive method that creates a menu to create a computer
	 * It wont allow the user to stop unless he added a name and a companyID to the new computer
	 * @param toCreate The computer that we want to create ,it starts as en empty one and
	 * 			we fill it recursively.
	 * @return The toCreate computer after we added a value
	 */
	
	public Computer createComputerMenu(Computer toCreate)
	{
		System.out.println("Choisissez quelle variable vous voulez entrez (Name et Company_id sont obligatoires);"
				+ "\n 1- Name" + "\n 2- Introduced (Date)" + "\n 3- Discontinued (Date)" + "\n 4- CompanyID" 
				+ "\n 5- J'ai entré toutes les données que je voulais");
	
		while (!scanner.hasNextInt()) scanner.next();
		int choice = scanner.nextInt();
		switch (choice)
		{
		case 1:
			scanner.nextLine();
			toCreate.setName(scanner.nextLine());
			createComputerMenu(toCreate);
			break;
		case 2:
			createComputerMenu(toCreate);
			break;
		case 3:
			createComputerMenu(toCreate);
			break;
		case 4:
			while (!scanner.hasNextInt()) scanner.next();
			toCreate.setCompanyID(scanner.nextInt());
			createComputerMenu(toCreate);
			break;
		case 5:
			if (toCreate.getName() == null ||toCreate.getCompanyID() == 0)
			{
				System.out.println("Vous n'avez pas entré toutes les données nécessaires");
				createComputerMenu(toCreate);
			}
			return toCreate;
		default:
			createComputerMenu(toCreate);
			break;
		}
		return toCreate;
	}
	
	/**
	 * A recursive method that creates a menu to update a computer
	 * 
	 * @param toUpdate The computer that we want to change the values
	 * 			It comes from a getById() in startComputer().
	 * @return The toUpdate computer with it's new values
	 */
	public Computer updateComputerMenu(Computer toUpdate)
	{
		System.out.println("Choisissez quelle variable vous voulez entrez (Name et Company_id sont obligatoires);"
				+ "\n 1- Name" + "\n 2- Introduced (Date)" + "\n 3- Discontinued (Date)" + "\n 4- CompanyID" 
				+ "\n 5- J'ai entré toutes les données que je voulais");
		
		int choice = scanner.nextInt();
		switch (choice)
		{
		case 1:
			scanner.nextLine();
			toUpdate.setName(scanner.nextLine());
			updateComputerMenu(toUpdate);
			break;
		case 2:
			updateComputerMenu(toUpdate);
			break;
		case 3:
			updateComputerMenu(toUpdate);
			break;
		case 4:
			while (!scanner.hasNextInt()) scanner.next();
			toUpdate.setCompanyID(scanner.nextInt());
			updateComputerMenu(toUpdate);
			break;
		case 5:
			return toUpdate;
		default:
			updateComputerMenu(toUpdate);
			break;
		}
		return toUpdate;
	}


	@Override
	public void exitMenu() {

		
	}

}
