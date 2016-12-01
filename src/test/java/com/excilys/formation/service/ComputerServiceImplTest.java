package com.excilys.formation.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerDaoImpl;

public class ComputerServiceImplTest {
    ComputerDaoImpl computerDao;

    @Before
    public void setUp() throws Exception {
       computerDao = mock(ComputerDaoImpl.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testComputerServiceImpl() {
        //ComputerServiceImpl computerServiceImpl = new ComputerServiceImpl();
        //assertNotNull(computerServiceImpl.dao)
        fail("Not yet implemented");
    }

    @Test
    public void testCreate() {
        Company company = new Company("test");
        company.setId(212);
        /*
        Computer computer = new Computer.ComputerBuilder(pComputerDto.getName()).manufacturer(company)
                .discontinued(ComputerAndDtoMapper.stringToLocalDate(pComputerDto.getDiscontinued()))
                .introduced(ComputerAndDtoMapper.stringToLocalDate(pComputerDto.getIntroduced())).build();
        */
    }

    @Test
    public void testDelete() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetById() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPage() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

}
