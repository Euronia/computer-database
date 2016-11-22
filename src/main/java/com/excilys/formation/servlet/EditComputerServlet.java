package com.excilys.formation.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.service.companyservice.companyserviceimpl.CompanyServiceImpl;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;

public class EditComputerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerDto computer = null;
        if (request.getParameter("id") != null) {
            ComputerService computerService = new ComputerServiceImpl();
            try {
                computer = computerService.getById(Integer.parseInt(request.getParameter("id")));
            } catch (NumberFormatException | ServiceException e) {
                e.printStackTrace();
            } 
        } else {
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);   
        }
        Page<CompanyDto> pageCompany = new Page<>(10);
        CompanyService companyService = new CompanyServiceImpl();
        pageCompany.setElementsByPage(10);
        try {
            companyService.getPage(pageCompany);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        pageCompany.setElementsByPage(pageCompany.getTotalElements());
        try {
            companyService.getPage(pageCompany);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        this.getServletContext().setAttribute("pageCompany", pageCompany);
        this.getServletContext().setAttribute("computer", computer);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
    }
    
}
