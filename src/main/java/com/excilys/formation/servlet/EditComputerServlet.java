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

public class EditComputerServlet extends HttpServlet {

    ////////// Parameters //////////

    private static Logger logger;
    private static final long serialVersionUID = 5820010289218504083L;
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
        ComputerDto computer = null;
        if (request.getParameter("id") != null) {
            try {
                computer = computerService.getById(Integer.parseInt(request.getParameter("id")));
            } catch (NumberFormatException | ServiceException e) {
                logger.error("EditComputerServlet : doGet(HttpServletRequest,HttpServletRequest) catched "
                        + e.getClass().toString());
                logger.error(e.getStackTrace().toString());
            }
        } else {
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        }
        Page<CompanyDto> pageCompany = new Page<>(10);
        pageCompany.setElementsByPage(10);
        try {
            companyService.getPage(pageCompany);
        } catch (ServiceException e) {
            logger.error(
                    "EditComputerServlet : doGet(HttpServletRequest,HttpServletRequest) catched ServiceException ");
            logger.error(e.getStackTrace().toString());
        }
        pageCompany.setElementsByPage(pageCompany.getTotalElements());
        try {
            companyService.getPage(pageCompany);
        } catch (ServiceException e) {
            logger.error(
                    "EditComputerServlet : doGet(HttpServletRequest,HttpServletRequest) catched ServiceException ");
            logger.error(e.getStackTrace().toString());
        }
        this.getServletContext().setAttribute("pageCompany", pageCompany);
        this.getServletContext().setAttribute("computer", computer);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerDto computerDto = new ComputerDto();
        computerDto.setId(Integer.parseInt(request.getParameter("id")));
        computerDto.setName(request.getParameter("name"));
        String introduced = request.getParameter("introduced");
        if (introduced != null && introduced.isEmpty()) {
            computerDto.setIntroduced(null);
        } else {
            computerDto.setIntroduced(introduced);
        }
        String discontinued = request.getParameter("discontinued");
        if (discontinued != null && discontinued.isEmpty()) {
            computerDto.setDiscontinued(null);
        } else {
            computerDto.setDiscontinued(discontinued);
        }
        String company = request.getParameter("companyId");
        if (company != null) {
            computerDto.setCompanyId(Integer.parseInt(company));
        }
        try {
            computerService.update(computerDto);
        } catch (ServiceException e) {
            logger.error(
                    "EditComputerServlet : doPost(HttpServletRequest,HttpServletRequest) catched ServiceException ");
            logger.error(e.getStackTrace().toString());
        }
        response.sendRedirect("/computerdatabase/dashboard");
    }
}