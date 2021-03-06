package com.excilys.formation.pagination;

import java.util.List;

public class Page<T> {

    ////////// Attributes //////////

    public int elementsPerPage;
    public int totalElements;
    public int currentPage;
    public int nbPages;
    public List<T> elements;

    ////////// Constructors //////////

    public Page() {
        currentPage = 1;
    }

    public Page(int elementsPerPage) {
        currentPage = 1;
        if (elementsPerPage == 0) {
            throw new RuntimeException("fy");
        }
        this.elementsPerPage = elementsPerPage;
    }

    ////////// Getters & Setters //////////

    public int getElementsByPage() {
        return elementsPerPage;
    }

    public void setElementsByPage(int elementsByPage) {
        this.elementsPerPage = elementsByPage;
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
        this.elements = elements;
    }

    public List<T> getElements() {
        return elements;
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
        } else {
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
        if (elementsPerPage != 0) {
            nbPages = (totalElements + elementsPerPage - 1) / elementsPerPage;
        } else {
            nbPages = 1;
        }
    }
}