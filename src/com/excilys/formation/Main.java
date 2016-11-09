/**
 * 
 */
package com.excilys.formation;

import java.util.List;

import com.excilys.formation.cli.MainMenu;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.persistence.*;

/**
 * @author Euronia
 * @version 1.0
 */
public class Main {

	public static ComputerDaoImpl daoComputer = new ComputerDaoImpl();
	/**
	 *  The main function of our program.
	 *  Creates a mainMenu and start its menu.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainMenu mainMenu = new MainMenu();
		mainMenu.startMenu();
	}

	
	/**
	 * A simple method to allow testing the daoComputer methods
	 * without having to use menus.
	 */
	public static void testJDBC()
	{
		List<Computer> computers = daoComputer.getList();
		for (Computer cp : computers)
		{
			System.out.println(cp.toString() + "\n");
		}
		
		//System.out.println(daoComputer.getComputerById(55).toString());
	
		//daoComputer.createComputer(new Computer("FireStarter 4587",3));
	
		Computer computer = daoComputer.getByID(62); 
		computer.setName("StarterFire 8745");
		daoComputer.update(computer);
		
		daoComputer.delete(60);
		
		computers = daoComputer.getList();
		for (Computer cp : computers)
		{
			System.out.println(cp.toString() + "\n");
		}
		
		
		//System.out.println(daoComputer.getComputerById(63).toString());
	}
}

