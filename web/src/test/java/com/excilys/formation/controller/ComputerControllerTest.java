package com.excilys.formation.controller;

import com.excilys.formation.config.SpringTestConfig;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.service.computerservice.ComputerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by excilys on 06/01/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
public class ComputerControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;



    @Test
    public void dashboardGet() throws Exception {

    }

    @Test
    public void dashboardPost() throws Exception {

    }

    @Test
    public void strings() throws Exception {

    }

    @Test
    public void editComputerGet() throws Exception {

    }

    @Test
    public void editComputerPost() throws Exception {

    }

    @Test
    public void addComputerGet() throws Exception {

    }

    @Test
    public void addComputerPost() throws Exception {

    }
}
