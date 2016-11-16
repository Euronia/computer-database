package com.excilys.formation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.service.ComputerServiceImpl;
import com.excilys.formation.service.exception.ServiceException;

/**
 * Servlet implementation class Dashboard
 */
public class Test extends HttpServlet {
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
        try {
            computerService.getPage(pageComputer);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        this.getServletContext().setAttribute("pageComputer", pageComputer);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/test.jsp").forward(request, response);
        /*response.setContentType("text/html");
        response.setCharacterEncoding( "UTF-8" );
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>Test</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>Ceci est une page générée depuis une servlet.</p>");
        out.println("</body>");
        out.println("</html>");*/
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}