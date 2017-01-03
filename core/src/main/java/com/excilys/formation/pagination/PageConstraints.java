package com.excilys.formation.pagination;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Euronia
 *
 */

public class PageConstraints {

    ////////// Parameters //////////

    private int currentPage;
    private int perPage;
    private Map<String, String> constraint = new HashMap<String, String>();
    private String filter;

    ////////// Getters And Setters //////////

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Map<String, String> getConstraint() {
        return constraint;
    }

    public void setConstraint(Map<String, String> contraintes) {
        this.constraint = contraintes;
    }

    ////////// Methods //////////

    public boolean hasFilter() {
        return (filter != null && !(filter.isEmpty()));
    }

    public void addConstraint(String key, String value) {
        this.constraint.put(key, value);
    }

}