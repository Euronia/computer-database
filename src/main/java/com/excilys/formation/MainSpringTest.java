package com.excilys.formation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.service.computerservice.computerserviceimpl.ComputerServiceImpl;

public class MainSpringTest {

    public static void main(String[] args) throws PersistenceException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Module.xml");

        ComputerService computerService = (ComputerService) ctx.getBean("computerService");
        ComputerDao computerDao = (ComputerDao) ctx.getBean("computerDao");
        Computer computer = new Computer.ComputerBuilder("computerSpring").manufacturer(new Company(1,"188Company")).build();
        try {
            computerService.create(ComputerAndDtoMapper.computerToDto(computer));
        } catch (ServiceException e) {
           System.out.println( "MainSpringTest : main() catched ServiceException");
        }

        Computer computerToFind = computerDao.getByName("azertyuiop");
        System.out.println(computerToFind);
        
    }
}
