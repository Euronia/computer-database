package com.excilys.formation.controller;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.service.computerservice.ComputerService;

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
    public ModelAndView addComputerGet() {
        ModelAndView model = new ModelAndView("addComputer");
        Page<CompanyDto> pageCompany = companyService.getAll();
        model.addObject("computerDto",new ComputerDto());
        model.addObject("pageCompany",pageCompany);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addComputerPost(@Valid @ModelAttribute("computerDto")ComputerDto computerDto, BindingResult bindingResult) {    
        ModelAndView model;
        if (!bindingResult.hasErrors()) {
            model = new ModelAndView("redirect:dashboard");
            try {
                computerService.create(computerDto);
            } catch (ServiceException e) {
                LOGGER.error( "EditComputerController : dashboardPost() catched ServiceException",e);
            }
        } else {
            model = new ModelAndView("addComputer");
        }
        return model;
    }

    ////////// Builder //////////
}
