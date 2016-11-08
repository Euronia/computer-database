/**
 * 
 */
package com.excilys.formation.tj.entities;

import java.sql.Date;

/**
 * @author Euronia
 * @version 1.0
 */
public class Computer {
	
	////////// Attributes //////////

	protected int id;
	protected String name;
	protected Date introduced = null; 
	protected Date discontinued = null;
	protected int company_id;
	
	////////// Constructors //////////
	
	public Computer ()
	{
		
	}
	
	public Computer (int cid)
	{
		this.id = cid;
	}
	
	public Computer ( String cname)
	{
		this.name = cname ;
	}
	
	public Computer ( String cname, int ccompany)
	{
		this.name = cname ;
		this.company_id = ccompany;
	}
	
	
	public Computer (int cid, String cname, Date cintroducedDate)
	{
		this.id = cid;
		this.name = cname ;
		this.introduced = cintroducedDate;
	}
	
	public Computer (int cid, String cname, Date cintroducedDate, Date cdiscontinuedDate)
	{
		this.id = cid;
		this.name = cname;
		this.introduced = cintroducedDate;
		this.discontinued = cdiscontinuedDate;
	}
	
	public Computer (int cid, String cname, Date cintroducedDate, Date cdiscontinuedDate, int ccompany)
	{
		this.id = cid;
		this.name = cname;
		this.introduced = cintroducedDate;
		this.discontinued = cdiscontinuedDate;
		this.company_id = ccompany;
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public int getCompanyID() {
		return company_id;
	}

	public void setCompanyID(int companyID) {
		this.company_id = companyID;
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
			returnString = returnString.concat(" owned by company [" + this.getCompanyID() + "]");
		return returnString;
		
	}
	
	
}