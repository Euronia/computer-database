package com.excilys.formation.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.util.MenuUtil;

public class RequestMapper {

    public ComputerDto toComputer (HttpServletRequest request) {
        String name = request.getParameter("name").trim();
        String introduced = request.getParameter("introduced").trim();
        String discontinued = request.getParameter("discontinued").trim();
        String companyId = request.getParameter("companyId").trim();
        ComputerDto returnComputer = new ComputerDto();
        if (name != null) {
            returnComputer.name = name;
        }
        if (introduced != null) {
            returnComputer.introduced = introduced;
        }
        if (discontinued != null) {
            returnComputer.discontinued = discontinued;
        }
        if (companyId != null && MenuUtil.isInteger(companyId)) {
            returnComputer.companyId = Integer.parseInt(companyId);
        }
        return returnComputer;
    }
    
    public static List<Integer> toList (HttpServletRequest request) {
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
