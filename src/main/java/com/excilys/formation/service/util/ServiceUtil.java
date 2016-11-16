package com.excilys.formation.service.util;

import com.excilys.formation.pagination.Page;

/**
 * A utilitary class for all services.
 * @author Euronia
 *
 */
public class ServiceUtil {
    /**
     * Copy attributes from to page to another, used when we need the same page
     * but with a different list of elements (like going from a DTO page to an
     * object page).
     * @param pPageToCopy the page we want the attributes of
     * @param pNewPage the page that'll receives the attribute
     */
    public static void copyAttributes(Page<?> pPageToCopy, Page<?> pNewPage) {
        pNewPage.setCurrentPage(pPageToCopy.getCurrentPage());
        pNewPage.setElementsByPage(pPageToCopy.getElementsByPage());
        pNewPage.nbPages = pPageToCopy.nbPages;
        pNewPage.setTotalElements(pPageToCopy.getTotalElements());
    }
}