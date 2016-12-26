package com.excilys.formation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    ////////// Fields //////////

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger("cdbLogger");
    
    @Autowired
    private ComputerService computerService;

    ////////// Constructors //////////

    ////////// Getters & Setters //////////

    ////////// Methods //////////

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView dashboardGet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/pages/dashboard.jsp");
        Page<Computer> pageComputer = new Page<>(10);
        PageConstraints constraints = ConstraintMapper.requestToContraintes(request);
        if (constraints.getFilter() != null) {
            model.addObject("filter", request.getParameter("search"));
        }
        pageComputer = computerService.getPage(constraints);
        model.addObject("pageComputer", ComputerAndDtoMapper.computerPageToDtoPage(pageComputer));
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView dashboardPost(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/pages/dashboard.jsp");
        Page<Computer> pageComputer = new Page<>(10);
        ComputerService computerService = new ComputerServiceImpl();
        PageConstraints constraints = ConstraintMapper.requestToContraintes(request);
        if (constraints.getFilter() != null) {
            model.addObject("filter", request.getParameter("search"));
        }
        pageComputer = computerService.getPage(constraints);
        model.addObject("pageComputer", ComputerAndDtoMapper.computerPageToDtoPage(pageComputer));
        return model;
    }

    ////////// Builder //////////
}
