package com.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.persistence.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.ComputerDaoImpl;
import com.excilys.formation.service.util.ServiceUtil;

/**
 * Service class for Computers.
 * @author Euronia
 *
 */
public class ComputerServiceImpl implements ComputerService {
    private ComputerDao computerDao;
    /**
     * Constructor for ComputerServiceImpl.
     * Initializes computerDao.
     */
    public ComputerServiceImpl() {
        computerDao = ComputerDaoImpl.getInstance();
    }
    @Override
    public ComputerDto create(ComputerDto pComputerDto) {
        try {
            Company company = new Company(pComputerDto.companyName);
            company.setId(pComputerDto.companyId);
            Computer computer = new Computer.ComputerBuilder(pComputerDto.name, company).setDiscontinued(pComputerDto.discontinued)
                    .setIntroduced(pComputerDto.introduced)      
                    .build();
            computerDao.create(computer);
            pComputerDto.id = computer.getId();
            return pComputerDto;
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void delete(int pId) {
        try {
            computerDao.delete(pId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ComputerDto getById(int pId) {
        Computer computer = null;
        try {
            computer = computerDao.getById(pId);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ComputerDto computerDto = null;
        if (computer != null) {
            computerDto = new ComputerDto();
            computerDto.id = computer.getId();
            computerDto.name = computer.getName();
            computerDto.introduced = computer.getIntroduced();
            computerDto.discontinued = computer.getDiscontinued();
            Company company = computer.getCompany();
            computerDto.companyId = company.getId();
            computerDto.companyName = company.getName();
        }
        return computerDto;
    }
    @Override
    public Page<ComputerDto> getPage(Page<ComputerDto> pPage) {
        Page<Computer> pageCompany = new Page<Computer>(10);
        ServiceUtil.copyAttributes(pPage, pageCompany);
        pageCompany.setElements(dtoListToComputerList(pPage.elements));
        try {
            pageCompany = computerDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ServiceUtil.copyAttributes(pageCompany, pPage);
        pPage.elements = computerListToDtoList(pageCompany.elements);
        return pPage;
    }
    @Override
    public ComputerDto update(ComputerDto pComputerDto) {
        Company company = new Company(pComputerDto.companyName);
        company.setId(pComputerDto.companyId);
        Computer computer = new Computer.ComputerBuilder(pComputerDto.name,company).setDiscontinued(pComputerDto.discontinued)
                .setIntroduced(pComputerDto.introduced).setId(pComputerDto.id)
                .build();
        try {
            computerDao.update(computer);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return computerToDto(computer);
    }
    /**
     * Converts a list from ComputerDto to Computer.
     * @param pListDto the list to convert
     * @return a Computer List
     */
    private List<Computer> dtoListToComputerList(List<ComputerDto> pListDto) {
        List<Computer> computers = null;
        if (pListDto != null) {
            computers = new ArrayList<>();
            for (ComputerDto computer : pListDto) {
                Company company = new Company(computer.companyName); 
                company.setId(computer.companyId);
                computers.add(new Computer.ComputerBuilder(computer.name,company)
                        .setIntroduced(computer.introduced).setDiscontinued(computer.discontinued).setId(computer.id)
                        .build());
            }
        }
        return computers;
    }
    /**
     * Converts a ComputerDto to Computer.
     * @param pDto the Dto to convert
     * @return a Computer
     */
    private Computer dtoToComputer(ComputerDto pDto) {
        Computer computer = null;
        if (pDto != null) {
                Company company = new Company(pDto.companyName); 
                company.setId(pDto.companyId);
                computer =new Computer.ComputerBuilder(pDto.name,company)
                        .setIntroduced(pDto.introduced).setDiscontinued(pDto.discontinued).setId(pDto.id)
                        .build();         
        }
        return computer;
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
                computerDto.id = computer.getId();
                computerDto.name = computer.getName();
                computerDto.introduced = computer.getIntroduced();
                computerDto.discontinued = computer.getDiscontinued();
                Company company = computer.getCompany();
                computerDto.companyId = company.getId();
                computerDto.companyName = company.getName();
                computersDto.add(computerDto);
            }
        }
        return computersDto;
    }
    
    private ComputerDto computerToDto(Computer pComp) {
        ComputerDto computerDto = new ComputerDto();
        computerDto.id = pComp.getId();
        computerDto.name = pComp.getName();
        computerDto.introduced = pComp.getIntroduced();
        computerDto.discontinued = pComp.getDiscontinued();
        Company company = pComp.getCompany();
        computerDto.companyId = company.getId();
        computerDto.companyName = company.getName();
        return computerDto;
    }
}