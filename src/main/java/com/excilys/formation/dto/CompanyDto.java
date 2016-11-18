
package com.excilys.formation.dto;

/**
 * DTO class for companies.
 * @author Euronia
 *
 */
public class CompanyDto {
    public int id;
    public String name;
    @Override
    public String toString() {
        return new StringBuilder().append("Company [id=").append(id).append(", name=").append(name).append("]")
                .toString();
    }
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
    
}