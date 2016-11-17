package com.excilys.formation.dto;

import java.time.LocalDate;

/**
 * DTO class for computers.
 * @author Euronia 
 *
 */
public class ComputerDto {
    public int id;
    public String name;
    public LocalDate introduced;
    public LocalDate discontinued;
    public int companyId;
    public String companyName;
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
    
    
}