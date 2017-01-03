package com.excilys.formation.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.computerservice.ComputerService;

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
        Page<ComputerDto> pageComputer = new Page<>(10);
        PageConstraints constraints = ConstraintMapper.requestToContraintes(request);
        if (constraints.getFilter() != null) {
            model.addObject("filter", request.getParameter("search"));
        }
        pageComputer = computerService.getPage(constraints);
        model.addObject("pageComputer",pageComputer);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView dashboardPost(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/pages/dashboard.jsp");
        Page<ComputerDto> pageComputer = new Page<>(10);
        PageConstraints constraints = ConstraintMapper.requestToContraintes(request);
        if (constraints.getFilter() != null) {
            model.addObject("filter", request.getParameter("search"));
        }     
        try {
            computerService.deleteMultiplesId(RequestMapper.toList(request));
        } catch (ServiceException e) {
            LOGGER.error( "DashboardController : dashboardPost() catched ServiceException",e);
        }
        pageComputer = computerService.getPage(constraints);
        model.addObject("pageComputer", pageComputer);
        return model;
    }
    
    @RequestMapping(path="strings.js")
    public ModelAndView strings(HttpServletRequest request)
    {
        Locale locale = RequestContextUtils.getLocale(request);
        ResourceBundle bundle = ResourceBundle.getBundle(("locales.messages"),locale);
        return new ModelAndView("/WEB-INF/pages/strings.jsp", "keys", bundle.getKeys());
    }

    ////////// Builder //////////
}
