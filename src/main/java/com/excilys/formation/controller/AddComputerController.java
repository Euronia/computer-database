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

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

    ////////// Constants //////////

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger("cdbLogger");

    ////////// Fields //////////

    @Autowired
    ComputerService computerService;
    
    @Autowired
    CompanyService companyService;

    ////////// Constructors //////////

    ////////// Getters & Setters //////////

    ////////// Methods //////////

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView dashboardGet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/pages/addComputer.jsp");
        Page<CompanyDto> pageCompany = companyService.getAll();
        model.addObject("pageCompany",pageCompany);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView dashboardPost(HttpServletRequest request, HttpServletResponse response) {
        ComputerDto computerDto = RequestMapper.toComputer(request);
        try {
            computerService.create(computerDto);
        } catch (ServiceException e) {
            LOGGER.error( "EditComputerController : dashboardPost() catched ServiceException",e);
        }
        return new ModelAndView("redirect:/dashboard");
    }

    ////////// Builder //////////
}
