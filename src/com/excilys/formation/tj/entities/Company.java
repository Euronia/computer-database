package com.excilys.formation.tj.entities;

import java.util.*;

/**
 * @author Euronia
 * @version 1.0
 */
public class Company {
	
	////////// Parameters //////////
	
	protected int id;
	protected String name;
	protected List<Computer> computers;
	
	////////// Constructors //////////
	
	public Company() 
	{
		computers = new ArrayList<Computer>();
	}
	
	public Company (int cid, String cname)
	{
		this.id = cid;
		this.name = cname;
		this.computers = new ArrayList<Computer>();
	}
	
	public Company (String cname)
	{
		this.name = cname;
		computers = new ArrayList<Computer>();
	}
	
	public Company (String cname, List<Computer> ccomputers)
	{
		this.name = cname;
		this.computers = ccomputers;
	}
	
	public Company (String cname, Computer ccomputer)
	{
		this.name = cname;
		computers = new ArrayList<Computer>();
		this.addComputer(ccomputer);
	}
	
	////////// Getters and Setters //////////
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	////////// Methods //////////
	
	public void addComputer (Computer pcomputer)
	{
		computers.add(pcomputer);
	}


	
}
