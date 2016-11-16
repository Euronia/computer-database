/**
 * 
 */
package com.excilys.formation.entity;

import java.time.LocalDate;

/**
 * @author Euronia
 * @version 1.0
 */
public class Computer {
	
	////////// Attributes //////////

	protected int id; //TODO KEEP IT ?
	protected String name;
	protected LocalDate introduced; 
	protected LocalDate discontinued;
	protected Company manufacturer;
	
	////////// Builder //////////
	
	public static class ComputerBuilder
	{
	    private int nestedId;
		private String nestedName;
		private LocalDate nestedIntroduced;
		private LocalDate nestedDiscontinued;
		private Company nestedManufacturer;
		
		public ComputerBuilder(String name, Company manufacturer)
		{
			nestedName = name;
			nestedManufacturer = manufacturer;
		}
		
		public ComputerBuilder setId(int pid)
		{
		    nestedId = pid;
		    return this;
		}
		
		public ComputerBuilder setName(String pname)
		{
			nestedName = pname;
			return this;
		}
		
		public ComputerBuilder setIntroduced(LocalDate pdate)
		{
			nestedIntroduced = pdate;
			return this;
		}
		
		public ComputerBuilder setDiscontinued(LocalDate pdate)
		{
			nestedDiscontinued = pdate;
			return this;
		}
		
		public ComputerBuilder setCompany(Company pmanufacturer)
		{
			nestedManufacturer = pmanufacturer;
			return this;
		}
		
		public Computer build()
		{
			return new Computer(this);
		}
	}
	////////// Constructors //////////
	
	public Computer (ComputerBuilder builder)
	{
	    id = builder.nestedId;
		name = builder.nestedName;
		introduced = builder.nestedIntroduced;
		discontinued = builder.nestedDiscontinued;
		manufacturer = builder.nestedManufacturer;
		
	}
	
	////////// Getters and Setters //////////

	public int getId() { 		//TODO delete it ?
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Company pmanufacturer) {	//TODO delete? 
		this.manufacturer = pmanufacturer;
	}
	
	////////// Methods //////////
	
	/**
	 * return the computer following this format
	 * [id] Name Introduced : (if!null) Discontinued : (if!null) owned by company company_id
	 */
	
	@Override 
	public String toString () {
		String returnString;
		returnString = "[" + this.getId() +
			"]: " +this.getName() ;
		if (this.getIntroduced() != null)
		{
			returnString =  returnString.concat(" Introduced : " + this.getIntroduced().toString());
		}
		if (this.getDiscontinued() != null)
		{
			returnString =  returnString.concat(" Discontinued : " + this.getDiscontinued().toString());
		}
			returnString = returnString.concat(" owned by company [" + this.getManufacturer() + "]");
		return returnString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
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
		Computer other = (Computer) obj;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    public Company getCompany() {
        return manufacturer;
    }
	
	
	
	
}