package com.excilys.formation.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

    ////////// Parameters //////////

    private static Logger logger;
    private static final long serialVersionUID = 1L;
    private CompanyService companyService;
    private ComputerService computerService;

    static {
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
    }

    ////////// Methods //////////

    public void init() {
        WebApplicationContext applicationContext = WebApplicationContextUtils
                .getWebApplicationContext(getServletContext());
        this.companyService = (CompanyService) applicationContext.getBean(CompanyService.class);
        this.computerService = (ComputerService) applicationContext.getBean(ComputerService.class);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Page<CompanyDto> pageCompany = new Page<>(10);
        pageCompany.setElementsByPage(10);
        try {
            companyService.getPage(pageCompany);
        } catch (ServiceException e) {
            logger.error(
                    "AddComputerServlet : doGet(HttpServletRequest, HttpServletRequest) catched ServiceException ");
            logger.error(e.getStackTrace().toString());
        }
        pageCompany.setElementsByPage(pageCompany.getTotalElements());
        try {
            companyService.getPage(pageCompany);
        } catch (ServiceException e) {
            logger.error(
                    "AddComputerServlet : doGet(HttpServletRequest, HttpServletRequest) catched ServiceException ");
            logger.error(e.getStackTrace().toString());
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
            logger.error(
                    "AddComputerServlet : doPost(HttpServletRequest, HttpServletRequest) catched ServiceException ");
            logger.error(e.getStackTrace().toString());
        }
        if (!ComputerValidator.validate(computerDto).isEmpty()) {
            // TODO : remonter les erreurs de la validation
            doGet(request, response);
        } else {
            doGet(request, response);
        }
    }
}