
package com.excilys.formation.dto;

/**
 * DTO class for companies.
 * 
 * @author Euronia
 *
 */
public class CompanyDto {

    ////////// Fields //////////

    public long id;
    public String name;

    ////////// Getters And Setters //////////

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public String toString() {
        return new StringBuilder().append("Company [id=").append(id).append(", name=").append(name).append("]")
                .toString();
    }
}