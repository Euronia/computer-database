package com.excilys.formation.dto;

/**
 * 
 * @author Euronia
 *
 */

public class ContraintesDto {

    ////////// Parameters //////////
    
    private int currentPage;
    private int perPage;
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
}