package com.excilys.formation.servlet;

import java.io.IOException;
import java.time.LocalDate;

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
import com.excilys.formation.validation.ComputerValidator;

/**
 * Servlet implementation class addComputer
 */
public class AddComputerServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerService computerService = new ComputerServiceImpl();
        ComputerDto computerDto = new ComputerDto();
        computerDto.setName(request.getParameter("name"));
        String introduced = request.getParameter("introduced");
        if (introduced.isEmpty()) {
            computerDto.setIntroduced(null);
        } else {
            computerDto.setIntroduced(introduced);
        }
        String discontinued = request.getParameter("discontinued");
        if (discontinued.isEmpty()) {
            computerDto.setDiscontinued(null);
        } else {
            computerDto.setDiscontinued(discontinued);
        }
        String company = request.getParameter("companyId");
        computerDto.setCompanyId(Integer.parseInt(company));
        try {
            computerService.create(computerDto);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (!ComputerValidator.validate(computerDto).isEmpty()) {
            // TODO : remonter les erreurs de la validation
            doGet(request, response);
        } else {
            doGet(request, response);
        }
    }
}