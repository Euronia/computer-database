package com.excilys.formation.mapper;

import com.excilys.formation.pagination.PageConstraints;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.pagination.Page;

import javax.servlet.http.HttpServletRequest;

public class ConstraintMapper {

    ////////// Constants //////////

    private static final String PAGE_PARAMETER = "page";
    private static final String PER_PAGE_PARAMETER = "perPage";
    private static final String SEARCH_PARAMETER = "search";
    private static final String ORDER_PARAMETER = "orderBy";

    ////////// Methods //////////

    public static PageConstraints requestToContraintes(HttpServletRequest request) {
        PageConstraints constraints = new PageConstraints();
        if (request.getParameter(PAGE_PARAMETER) == null || request.getParameter(PAGE_PARAMETER) == "") {
            constraints.setCurrentPage(1);
        } else if (request.getParameter(PAGE_PARAMETER) != null) {
            constraints.setCurrentPage(Integer.parseInt(request.getParameter(PAGE_PARAMETER)));
        }
        if (request.getParameter(PER_PAGE_PARAMETER) == null || !(request.getParameter(PER_PAGE_PARAMETER).isEmpty())) {
            constraints.setPerPage(10);
        } else {
            constraints.setPerPage(Integer.parseInt(request.getParameter(PER_PAGE_PARAMETER)));
        }
        request.getServletContext().setAttribute("filter", null);
        if (request.getParameter(SEARCH_PARAMETER) != null
                && !(request.getParameter(SEARCH_PARAMETER).trim().isEmpty())) {
            constraints.addConstraint("filter", "%" + request.getParameter(SEARCH_PARAMETER) + "%");
            constraints.setFilter(request.getParameter(SEARCH_PARAMETER));

            request.getServletContext().setAttribute("filter", request.getParameter(SEARCH_PARAMETER));
        }
        if (request.getParameter(ORDER_PARAMETER) != null) {
            //TODO actually toredo
        }
        return constraints;
    }

    public static Page<Computer> contraintesToComputerPage(PageConstraints constraints) {
        Page<Computer> returnPage = new Page<Computer>(constraints.getPerPage());
        returnPage.setCurrentPage(constraints.getCurrentPage());
        return returnPage;
    }

}
