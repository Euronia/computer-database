package com.excilys.formation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.persistence.computerdao.ComputerDao;

public class MainSpringTest {

    public static void main(String[] args) throws PersistenceException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Module.xml");

        
        ComputerDao computerDao = (ComputerDao) ctx.getBean("computerDao");
        Computer computer = new Computer.ComputerBuilder("computerSpring").manufacturer(new Company(1,"188Company")).build();
        computerDao.create(computer);

        Computer computerToFind = computerDao.getByName("azertyuiop");
        System.out.println(computerToFind);
        

    }
}
