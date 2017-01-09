package com.excilys.formation.controller;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.pagination.PageConstraints;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.service.computerservice.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by excilys on 05/01/17.
 */

@RestController
@RequestMapping("/")
public class ComputerController {

    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger("cdbLogger");

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardGet(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("dashboard");
        Page<ComputerDto> pageComputer ;
        PageConstraints constraints = ConstraintMapper.requestToContraintes(request);
        if (constraints.getFilter() != null) {
            model.addObject("filter", request.getParameter("search"));
        }
        pageComputer = computerService.getPage(constraints);
        model.addObject("pageComputer",pageComputer);
        return model;
    }

    @RequestMapping(path= "/dashboard", method = RequestMethod.POST)
    public ModelAndView dashboardPost(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("dashboard");
        Page<ComputerDto> pageComputer;
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

    @RequestMapping(path="/strings.js")
    public ModelAndView strings(HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);
        ResourceBundle bundle = ResourceBundle.getBundle(("locales.messages"), locale);
        return new ModelAndView("strings", "keys", bundle.getKeys());
    }

    @RequestMapping(path="/editComputer", method = RequestMethod.GET)
    public ModelAndView editComputerGet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("editComputer");
        if (request.getParameter("id") != null) {
            try {
                ComputerDto computer = computerService.getById(Long.parseLong(request.getParameter("id")));
                model.addObject("computer",computer);
            } catch (NumberFormatException e) {
                LOGGER.error("EditComputerController : dashboardGet() catched Exception", e);
            }
        } else {
            return new ModelAndView("dashboard");
        }
        Page<CompanyDto> pageCompany = companyService.getAll();
        model.addObject("pageCompany",pageCompany);
        return model;
    }

    @RequestMapping(path = "/editComputer", method = RequestMethod.POST)
    public ModelAndView editComputerPost(HttpServletRequest request) {
        ComputerDto computerDto = RequestMapper.toComputer(request);
        try {
            computerService.update(computerDto);
        } catch (ServiceException e) {
            LOGGER.error( "EditComputerController : dashboardPost() catched ServiceException",e);
        }
        return new ModelAndView("redirect:/dashboard");
    }

    @RequestMapping(path = "/addComputer", method = RequestMethod.GET)
    public ModelAndView addComputerGet() {
        ModelAndView model = new ModelAndView("addComputer");
        Page<CompanyDto> pageCompany = companyService.getAll();
        model.addObject("computerDto",new ComputerDto());
        model.addObject("pageCompany",pageCompany);
        return model;
    }

    @RequestMapping(path = "/addComputer", method = RequestMethod.POST)
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
}
