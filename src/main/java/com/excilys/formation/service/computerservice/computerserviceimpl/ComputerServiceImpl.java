package com.excilys.formation.service.computerservice.computerserviceimpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerDaoImpl;
import com.excilys.formation.service.computerservice.ComputerService;
import com.excilys.formation.util.ServiceUtil;

/**
 * Service class for Computers.
 * @author Euronia
 *
 */
public class ComputerServiceImpl implements ComputerService {
    
    ////////// Parameters //////////
    
    private ComputerDao computerDao;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static Logger logger;
    
    static{
        logger = LoggerFactory.getLogger("cdbLogger");
    }
    
    /**
     * Constructor for ComputerServiceImpl.
     * Initializes computerDao.
     */
    
    public ComputerServiceImpl() {
        computerDao = ComputerDaoImpl.getInstance();
    }
    
    private String localDateToString(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
    
    private LocalDate stringToLocalDate(String date) {
        if (date == null) {
            return null;
        } else {
            return LocalDate.parse(date,formatter);
        }
    }
    @Override
    public ComputerDto create(ComputerDto pComputerDto) {
        try {
            Company company = new Company(pComputerDto.getCompanyName());
            company.setId(pComputerDto.getCompanyId());
            Computer computer = new Computer.ComputerBuilder(pComputerDto.getName()).manufacturer(company).discontinued(stringToLocalDate(pComputerDto.getDiscontinued()))
                    .introduced(stringToLocalDate(pComputerDto.getIntroduced()))     
                    .build();
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
    public ComputerDto getById(int pId) {
        Computer computer = null;
        try {
            computer = computerDao.getById(pId);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : getById(int) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        ComputerDto computerDto = null;
        if (computer != null) {
            computerDto = new ComputerDto();
            computerDto.setId(computer.getId());
            computerDto.setName(computer.getName());
            computerDto.setIntroduced(localDateToString(computer.getIntroduced()));
            computerDto.setDiscontinued(localDateToString(computer.getDiscontinued()));
            Company company = computer.getManufacturer();
            computerDto.setCompanyId(company.getId());
            computerDto.setCompanyName(company.getName());
        }
        return computerDto;
    }

    @Override
    public Page<ComputerDto> getPage(Page<ComputerDto> page) {
        Page<Computer> pageCompany = new Page<Computer>(10);
        ServiceUtil.copyAttributes(page, pageCompany);
        pageCompany.setElements(dtoListToComputerList(page.elements));
        try {
            pageCompany = computerDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : getPage(Page<ComputerDto>) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        ServiceUtil.copyAttributes(pageCompany, page);
        page.elements = computerListToDtoList(pageCompany.elements);
        return page;
    }

    /**
     * 
     * @param
     * @return 
     */
    public Page<ComputerDto> getPageFilter(Page<ComputerDto> page, String filter) {
        Page<Computer> pageCompany = new Page<Computer>(10);
        ServiceUtil.copyAttributes(page, pageCompany);
        pageCompany.setElements(dtoListToComputerList(page.elements));
        try {
            computerDao.getAllFilter(pageCompany,filter);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : getPageFilter(Page<ComputerDto>, String) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        ServiceUtil.copyAttributes(pageCompany, page);
        page.elements = computerListToDtoList(pageCompany.elements);   
        return page;    
    }

    @Override
    public ComputerDto update(ComputerDto pComputerDto) {
        Company company = new Company(pComputerDto.getCompanyName());
        company.setId(pComputerDto.getCompanyId());
        Computer computer = new Computer.ComputerBuilder(pComputerDto.getName())
                .discontinued(stringToLocalDate(pComputerDto.getDiscontinued())).introduced(stringToLocalDate(pComputerDto.getIntroduced()))
                .id(pComputerDto.getId()).manufacturer(company).build();
        try {
            computerDao.update(computer);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : update(ComputerDto) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        return computerToDto(computer);
    }

    /**
     * Converts a list from ComputerDto to Computer.
     * @param listDto the list to convert
     * @return a Computer List
     */
    private List<Computer> dtoListToComputerList(List<ComputerDto> listDto) {
        List<Computer> computers = null;
        if (listDto != null) {
            computers = new ArrayList<>();
            for (ComputerDto computer : listDto) {
                Company company = new Company(computer.getCompanyName()); 
                company.setId(computer.getCompanyId());
                computers.add(new Computer.ComputerBuilder(computer.getName()).manufacturer(company)
                        .introduced(stringToLocalDate(computer.getIntroduced())).discontinued(stringToLocalDate(computer.getDiscontinued())).id(computer.getId())
                        .build());
            }
        }
        return computers;
    }

    /**
     * Converts a list from Computer to ComputerDto.
     * @param pList the list to convert
     * @return a ComputerDto List
     */
    private List<ComputerDto> computerListToDtoList(List<Computer> pList) {
        List<ComputerDto> computersDto = null;
        if (pList != null) {
            computersDto = new ArrayList<>();
            for (Computer computer : pList) {
                ComputerDto computerDto = new ComputerDto();
                computerDto.setId(computer.getId());
                computerDto.setName(computer.getName());
                if (computer.getIntroduced() != null) {
                    computerDto.setIntroduced(computer.getIntroduced().toString());
                } else {
                    computerDto.setIntroduced(null);
                }
                if (computer.getDiscontinued() != null) {
                    computerDto.setDiscontinued(computer.getDiscontinued().toString());
                } else {
                    computerDto.setDiscontinued(null);
                }
                Company company = computer.getManufacturer();
                computerDto.setCompanyId(company.getId());
                computerDto.setCompanyName(company.getName());
                computersDto.add(computerDto);
            }
        }
        return computersDto;
    }

    /**
     * Transform a Computer into a ComputerDto.
     * @param pComp the Computer we want to transform
     * @return the parameter computer transformed into a ComputerDto
     */
    private ComputerDto computerToDto(Computer pComp) {
        ComputerDto computerDto = new ComputerDto();
        computerDto.setId(pComp.getId());
        computerDto.setName(pComp.getName());
        if (pComp.getIntroduced() != null) { 
            computerDto.setIntroduced(pComp.getIntroduced().toString());
        }
        if (pComp.getDiscontinued() != null) {
            computerDto.setDiscontinued(pComp.getDiscontinued().toString());
        }        
        Company company = pComp.getManufacturer();
        computerDto.setCompanyId(company.getId());
        computerDto.setCompanyName(company.getName());
        return computerDto;
    }

    /**
     * Calls the delete() method on all the ID in the parameter List.
     * @param ids A list of integers representing the Ids to delete.
     */
    public void deleteMultiplesId(List<Integer> ids) {
        for (int id : ids) {
            delete(id);
        }
    }
}