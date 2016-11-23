package com.excilys.formation.entity;

/**
 * @author Euronia
 * @version 1.0
 */
public class Company {
	
	////////// Parameters //////////
	

	protected int id;
	protected String name;
	
	////////// Constructors //////////
	
	public Company (String cname) {
		this.name = cname;
	}
	
	public Company (int id, String name) {
	    this.id = id;
	    this.name = name;
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

	////////// Methods //////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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