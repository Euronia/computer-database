package com.excilys.formation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.util.MenuUtil;

/**
 * Mapper class to be used in different servlets
 * 
 * @author Euronia
 *
 */

public class RequestMapper {

    /**
     * This method allows us to transform a ServletRequest in a ComputerDto.
     * 
     * @param request A request form a servlet, it must contain a computer.
     * @return A ComputerDto based on the parameters contained in the request
     */
    public static ComputerDto toComputer(HttpServletRequest request) {
        // getParameter returns an empty Enum if the parameter is not found
        String id = request.getParameter("id");
        String name = request.getParameter("name").trim();
        String introduced = request.getParameter("introduced").trim();
        String discontinued = request.getParameter("discontinued").trim();
        String companyId = request.getParameter("companyId").trim();
        ComputerDto returnComputer = new ComputerDto();
        if (id != null) {
            returnComputer.setId(Long.parseLong(id));
        }
        if (name != null) {
            returnComputer.setName(name);
        }
        if (introduced != null) {
            returnComputer.setIntroduced(introduced);
        }
        if (discontinued != null) {
            returnComputer.setDiscontinued(discontinued);
        }
        if (companyId != null && MenuUtil.isInteger(companyId)) {
            returnComputer.setCompanyId(Integer.parseInt(companyId));
        }
        return returnComputer;
    }

    /**
     * This method returns a list of Integer from a request.
     * 
     * @param request A request from a Servlet
     * @return A List of all the integers contained in the request
     */
    public static List<Integer> toList(HttpServletRequest request) {
        List<Integer> returnList = new ArrayList<Integer>();
        String[] idArray = request.getParameter("selection").trim().split(",");
        for (String id : idArray) {
            if (MenuUtil.isInteger(id)) {
                returnList.add(Integer.parseInt(id));
            }
        }
        return returnList;
    }
}
