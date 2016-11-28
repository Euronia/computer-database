package com.excilys.formation.dto;

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
    private Map<String, String> contraintes;
    private String filter;

    ///////// Constructors ///////////

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

    public Map<String, String> getContraintes() {
        return contraintes;
    }

    public void setContraintes(Map<String, String> contraintes) {
        this.contraintes = contraintes;
    }
    
    ////////// Methods //////////
    
    public boolean hasFilter() {
        return (filter != null && !(filter.isEmpty()));
    }

}