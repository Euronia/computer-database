package com.excilys.formation.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;

/**
 * Servlet implementation class Dashboard
 */
public class DashboardServlet extends HttpServlet {
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
        Page<ComputerDto> pageComputer = new Page<>(10);
        ComputerService computerService = new ComputerServiceImpl();
        if (request.getParameter("page") == "") {
            pageComputer.setCurrentPage(1); 
        } else if (request.getParameter("page") != null) {
            pageComputer.setCurrentPage(Integer.parseInt(request.getParameter("page")));
        } 
        if (request.getParameter("perPage") != null) {
            pageComputer.setElementsByPage(Integer.parseInt(request.getParameter("perPage")));
        }
        if (request.getParameter("search") != null) {
                computerService.getPageFilter(pageComputer, request.getParameter("search"));     
                this.getServletContext().setAttribute("filter", request.getParameter("search"));
        } else {
            try {
                computerService.getPage(pageComputer);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        this.getServletContext().setAttribute("pageComputer", pageComputer);
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
            System.out.println(RequestMapper.toList(request));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }
}