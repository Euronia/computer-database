package com.excilys.formation.service.computerservice.computerserviceimpl;

import java.util.List;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerDaoImpl;
import com.excilys.formation.service.computerservice.ComputerService;

/**
 * Service class for Computers.
 * 
 * @author Euronia
 *
 */

@Service
public class ComputerServiceImpl implements ComputerService {

    ////////// Parameters //////////

    @Autowired
    private ComputerDao computerDao;
    @Autowired
    private ComputerAndDtoMapper computerMapper;
    
    private static Logger logger;

    static {
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
    }

    public void setComputerDao(ComputerDao comp) {
        computerDao = comp;
    }

    /**
     * Constructor for ComputerServiceImpl. Initializes computerDao.
     */

    public ComputerServiceImpl() {
        computerDao = ComputerDaoImpl.getInstance();
    }

    ////////// Getters and Setters //////////

    @Override
    public ComputerDto create(ComputerDto pComputerDto) {
        try {

            Company company = new Company(pComputerDto.getCompanyName());
            company.setId(pComputerDto.getCompanyId());
            Computer computer = new Computer.Builder(pComputerDto.getName()).manufacturer(company)
                    .discontinued(computerMapper.stringToLocalDate(pComputerDto.getDiscontinued()))
                    .introduced(computerMapper.stringToLocalDate(pComputerDto.getIntroduced())).build();
            computerDao.create(computer);
            pComputerDto.setId(computer.getId());
            return pComputerDto;
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : create(ComputerDto) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        return null;
    }

    @Override
    public void delete(long id) {
        try {
            computerDao.delete(id);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : delete(long) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
    }

    @Override
    public ComputerDto getById(long pId) {
        Computer computer = null;
        try {
            System.out.println(pId);
            computer = computerDao.getById(pId);
            System.out.println(computer);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : getById(int) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        ComputerDto computerDto = null;
        if (computer != null) {
            computerDto = new ComputerDto();
            computerDto.setId(computer.getId());
            computerDto.setName(computer.getName());
            computerDto.setIntroduced(computerMapper.localDateToString(computer.getIntroduced()));
            computerDto.setDiscontinued(computerMapper.localDateToString(computer.getDiscontinued()));
            Company company = computer.getManufacturer();
            computerDto.setCompanyId(company.getId());
            computerDto.setCompanyName(company.getName());
        }
        return computerDto;
    }

    /**
     * Returns a page of Computer respecting the constraints set in parameters.
     * 
     * @param constraints all the constraints that our page must respects.
     * @return a page of Computer
     */
    public Page<ComputerDto> getPage(PageConstraints constraints) {
        Page<Computer> pageComputer = ConstraintMapper.contraintesToComputerPage(constraints);
        try {
            pageComputer = computerDao.getPage(pageComputer, constraints);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : getPage(Page<ComputerDto>) catched PersistenceException ",e);
        }
        Page<ComputerDto> returnPage = computerMapper.dtoPagetoComputerPage(pageComputer);
        return returnPage;
    }

    @Override
    public ComputerDto update(ComputerDto pComputerDto) {
        Company company = new Company(pComputerDto.getCompanyId(), pComputerDto.getCompanyName());
        Computer computer = new Computer.Builder(pComputerDto.getName())
                .discontinued(computerMapper.stringToLocalDate(pComputerDto.getDiscontinued()))
                .introduced(computerMapper.stringToLocalDate(pComputerDto.getIntroduced()))
                .id(pComputerDto.getId()).manufacturer(company).build();
        try {
            computerDao.update(computer);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : update(ComputerDto) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        return ComputerAndDtoMapper.computerToDto(computer);
    }

    /**
     * Calls the delete() method on all the ID in the parameter List.
     * 
     * @param ids A list of integers representing the Ids to delete.
     */
    public void deleteMultiplesId(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }
}