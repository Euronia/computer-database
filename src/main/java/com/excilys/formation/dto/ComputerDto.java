package com.excilys.formation.dto;

/**
 * DTO class for computers.
 * @author Euronia 
 *
 */
public class ComputerDto {

    ////////// Parameters //////////

    public int id;
    public String name;
    public String introduced;
    public String discontinued;
    public int companyId;
    public String companyName;
    
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
    public String getIntroduced() {
        return introduced;
    }
    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }
    public String getDiscontinued() {
        return discontinued;
    }
    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }
    public int getCompanyId() {
        return companyId;
    }
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    ////////// Methods //////////
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("Computer : [id=").append(id).append(", name=")
                .append(name);
        if (introduced != null) {
            stringBuilder.append(", introduced=").append(introduced);
        }
        if (discontinued != null) {
            stringBuilder.append(", discontinued=").append(discontinued);
        }
        if (companyId != 0 && companyName != null) {
            stringBuilder.append(", Company : [companyId=").append(companyId).append(", companyName=")
                    .append(companyName).append("]]");
        }
        return stringBuilder.toString();
    }
}