package com.excilys.formation.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;

/**
 * Servlet implementation class Dashboard
 */
public class DashboardServlet extends HttpServlet {

    ////////// Parameters //////////

    private static Logger logger;
    private static final long serialVersionUID = 6163744348925320231L;

    static {
        logger = (Logger) LoggerFactory.getLogger(DashboardServlet.class);
    }

    ////////// Methods //////////

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Page<Computer> pageComputer = new Page<>(10);
        ComputerService computerService = new ComputerServiceImpl();
        PageConstraints constraints = ConstraintMapper.requestToContraintes(request);
        if (constraints.getFilter() != null) {
            this.getServletContext().setAttribute("filter", request.getParameter("search"));
        }
        pageComputer = computerService.getPage(constraints);
        this.getServletContext().setAttribute("pageComputer", ComputerAndDtoMapper.computerPageToDtoPage(pageComputer));
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerService computerService = new ComputerServiceImpl();
        try {
            computerService.deleteMultiplesId(RequestMapper.toList(request));
        } catch (ServiceException e) {
            logger.error("DashboardServlet : doPost(HttpServletRequest, HttpServletRequest) catched ServiceException ");
            logger.error(e.getStackTrace().toString());
        }
        doGet(request, response);
    }
}