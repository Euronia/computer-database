package com.excilys.formation.entity;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Euronia
 *
 */
public final class Computer {

    ////////// Attributes //////////

    private long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company manufacturer;
    private static Logger logger;

    static {
        logger = LoggerFactory.getLogger("cdbLogger");
    }

    ////////// Constructors //////////

    private Computer() {
    }

    private Computer(Builder builder) {
        id = builder.nestedId;
        name = builder.nestedName;
        introduced = builder.nestedIntroduced;
        discontinued = builder.nestedDiscontinued;
        manufacturer = builder.nestedManufacturer;
    }

    ////////// Getters and Setters //////////

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

    public void setManufacturer(Company pmanufacturer) { // TODO delete?
        this.manufacturer = pmanufacturer;
    }

    ////////// Methods //////////

    /**
     * return the computer following this format [id] Name Introduced :
     * (if!null) Discontinued : (if!null) owned by company company_id
     */

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        returnString.append("[").append(this.getId()).append("]: ").append(this.getName());
        if (this.getIntroduced() != null) {
            returnString.append(" Introduced : ").append(this.getIntroduced().toString());
        }
        if (this.getDiscontinued() != null) {
            returnString.append(" Discontinued : ").append(this.getDiscontinued().toString());
        }
        returnString.append(" owned by company [").append(this.getManufacturer()).append("]");
        return returnString.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
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

    ////////// Builder //////////

    public static class Builder {
        private long nestedId;
        private String nestedName;
        private LocalDate nestedIntroduced;
        private LocalDate nestedDiscontinued;
        private Company nestedManufacturer;

        public Builder(String name) {
            nestedName = name;
        }

        public Builder id(long id) {
            nestedId = id;
            return this;
        }

        public Builder name(String pname) {
            nestedName = pname;
            return this;
        }

        public Builder introduced(LocalDate pdate) {
            nestedIntroduced = pdate;
            return this;
        }

        public Builder discontinued(LocalDate pdate) {
            nestedDiscontinued = pdate;
            return this;
        }

        public Builder manufacturer(Company pmanufacturer) {
            nestedManufacturer = pmanufacturer;
            return this;
        }

        public Computer build() throws IllegalArgumentException {
            if (nestedName.trim().length() < 3) {
                logger.error("Computer: ComputerBuilder: build() throwed IllegalArgumentException");
                throw new IllegalArgumentException("Invalid name");
            }
            return new Computer(this);
        }
    }
}