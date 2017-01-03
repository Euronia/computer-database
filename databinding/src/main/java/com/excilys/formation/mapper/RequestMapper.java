package com.excilys.formation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.dto.ComputerDto;

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
            returnComputer.setDiscontinued(discontinued);
        if (companyId != null && isInteger(companyId)) {
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
    public static List<Long> toList(HttpServletRequest request) {
        List<Long> returnList = new ArrayList<Long>();
        String[] idArray = request.getParameter("selection").trim().split(",");
        for (String id : idArray) {
            if (isInteger(id)) {
                returnList.add(Long.parseLong(id));
            }
        }
        return returnList;
    }

    /**
     * Checks if a string is an integer.
     *
     * @param stringToCheck the String to check
     * @return a boolean
     */
    public static boolean isInteger(String stringToCheck) {
        if (stringToCheck == null) {
            return false;
        }
        int length = stringToCheck.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        // we check if the string starts with "-" and the length is > 1
        if (stringToCheck.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        // we check each char of the string to see if it's a number
        for (; i < length; i++) {
            char c = stringToCheck.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
