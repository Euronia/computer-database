package com.excilys.formation.pagination;

import java.util.List;

public class Page<T> {

    ////////// Attributes //////////

    public int elementsByPage;
    public int totalElements;
    public int currentPage;
    public int nbPages;
    public List<T> elements;
    
    ////////// Constructors //////////
    
    public Page() {
        currentPage = 1;
    }
    
    public Page(int pelementsByPage)
    {
        currentPage = 1;
        elementsByPage = pelementsByPage;
    }
    
    ////////// Getters & Setters //////////

    public int getElementsByPage() {
        return elementsByPage;
    }

    public void setElementsByPage(int elementsByPage) {
        this.elementsByPage = elementsByPage;
        calculateNbPages();
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
        calculateNbPages();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getNbPages() {
        return nbPages;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }
    
    public void setElements(List<T> elements) {
        this.elements = elements ;
    }


    ////////// Methods //////////
    

    public boolean nextPage() {
        if (currentPage < nbPages) {
            currentPage++;
            return true;
        }
        return false;
    }
    
    public boolean prevPage() {
        if (currentPage > 1) {
            currentPage--;
            return true;
        }else { 
            return false;
        }
    }
    
    public boolean setPage(int page) {
        if (page >= 1 && page <= nbPages) {
            currentPage = page;
            return true;
        } else {
            return false;
        }
    }
    
    public void calculateNbPages() {
        nbPages = (totalElements + elementsByPage -1) / elementsByPage;
    }
 

}
