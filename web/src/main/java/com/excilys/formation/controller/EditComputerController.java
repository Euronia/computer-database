package com.excilys.formation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.service.computerservice.ComputerService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {

    ////////// Constants //////////

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger("cdbLogger");

    ////////// Fields //////////

    @Autowired
    ComputerService computerService;
    
    @Autowired
    CompanyService companyService;
    
    ////////// Methods //////////

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView editComputerGet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("editComputer");
        if (request.getParameter("id") != null) {
            try {
                ComputerDto computer = computerService.getById(Long.parseLong(request.getParameter("id")));
                model.addObject("computer",computer);
            } catch (NumberFormatException | ServiceException e) {
                LOGGER.error("EditComputerController : dashboardGet() catched Exception", e);
            }
        } else {
            return new ModelAndView("dashboard");
        }
        Page<CompanyDto> pageCompany = companyService.getAll();
        model.addObject("pageCompany",pageCompany);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView editComputerPost(HttpServletRequest request) {
        ComputerDto computerDto = RequestMapper.toComputer(request);
        try {
            computerService.update(computerDto);
        } catch (ServiceException e) {
            LOGGER.error( "EditComputerController : dashboardPost() catched ServiceException",e);
        }
        return new ModelAndView("redirect:/dashboard");
    }
}
