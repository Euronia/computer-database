package com.excilys.formation.entity;

/**
 * @author Euronia
 * 
 */
public final class Company {

    ////////// Parameters //////////

    private long id;
    private String name;

    ////////// Constructors //////////

    public Company(String cname) {
        this.name = cname;
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
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

    ////////// Methods //////////

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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