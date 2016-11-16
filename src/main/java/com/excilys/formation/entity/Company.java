package com.excilys.formation.entity;

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
	
	public Company (String cname)
	{
		this.name = cname;
		computers = new ArrayList<Computer>();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((computers == null) ? 0 : computers.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (computers == null) {
			if (other.computers != null)
				return false;
		} else if (!computers.equals(other.computers))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	
}
