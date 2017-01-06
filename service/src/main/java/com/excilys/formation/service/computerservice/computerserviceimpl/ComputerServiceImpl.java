package com.excilys.formation.service.computerservice.computerserviceimpl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.pagination.PageConstraints;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerRepository;
import com.excilys.formation.service.computerservice.ComputerService;

import ch.qos.logback.classic.Logger;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComputerServiceImpl implements ComputerService {
    
    @Autowired
    ComputerRepository computerRepository;

    @Autowired
    private ComputerAndDtoMapper computerMapper;
    
    private static Logger logger;

    static {
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
    }

    @Transactional
    @Override
    public ComputerDto create(ComputerDto pComputerDto) {
        Company company = new Company(pComputerDto.getCompanyName());
        company.setId(pComputerDto.getCompanyId());
        Computer computer = new Computer.Builder(pComputerDto.getName()).manufacturer(company)
                .discontinued(computerMapper.stringToLocalDate(pComputerDto.getDiscontinued()))
                .introduced(computerMapper.stringToLocalDate(pComputerDto.getIntroduced())).build();
        computerRepository.save(computer);
        pComputerDto.setId(computer.getId());
        return pComputerDto;
    }

    @Transactional
    @Override
    public void delete(long id) {
        computerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ComputerDto getById(long pId) {
        Computer computer = null;
        computer = computerRepository.findById(pId);
        ComputerDto computerDto = null;
        if (computer != null) {
            computerDto = new ComputerDto();
            computerDto.setId(computer.getId());
            computerDto.setName(computer.getName());
            computerDto.setIntroduced(computerMapper.localDateToString(computer.getIntroduced()));
            computerDto.setDiscontinued(computerMapper.localDateToString(computer.getDiscontinued()));
            Company company = computer.getCompany();
            if (company != null) {
                computerDto.setCompanyId(company.getId());
                computerDto.setCompanyName(company.getName());
            } else {
                computerDto.setCompanyId(0);
            }
        }
        return computerDto;
    }

    /**
     * Returns a page of Computer respecting the constraints set in parameters.
     * 
     * @param constraints all the constraints that our page must respects.
     * @return a page of Computer
     */
    @Transactional
    public Page<ComputerDto> getPage(PageConstraints constraints) {
        Page<Computer> pageComputer = ConstraintMapper.contraintesToComputerPage(constraints);
        PageRequest pageRequest = new PageRequest(pageComputer.currentPage-1,(int) pageComputer.elementsPerPage);
        if (constraints.getFilter() != null) {
            pageComputer.setElements(computerRepository.findByNameContaining(constraints.getFilter()));
        } else {
            pageComputer.setElements(computerRepository.findAll(pageRequest).getContent());
        }
        pageComputer.setTotalElements(computerRepository.count());
        Page<ComputerDto> returnPage = computerMapper.dtoPagetoComputerPage(pageComputer);
        return returnPage;
    }

    @Transactional
    public Page<ComputerDto> getPage(Page<ComputerDto> page)
    {
        Page<Computer> pageComputer = new Page<>(10);
        ComputerAndDtoMapper.copyAttributes(page,pageComputer);
        pageComputer.setElements(computerMapper.dtoListToComputerList(page.getElements()));
        PageRequest pageRequest = new PageRequest(pageComputer.currentPage-1,(int) pageComputer.elementsPerPage);
        pageComputer.setElements(computerRepository.findAll(pageRequest).getContent());
        pageComputer.setTotalElements(computerRepository.count());
        Page<ComputerDto> returnPage = computerMapper.dtoPagetoComputerPage(pageComputer);
        return returnPage;
    }

    @Transactional
    @Override
    public ComputerDto update(ComputerDto pComputerDto) {
        Company company = new Company(pComputerDto.getCompanyId(), pComputerDto.getCompanyName());
        Computer computer = new Computer.Builder(pComputerDto.getName())
                .discontinued(computerMapper.stringToLocalDate(pComputerDto.getDiscontinued()))
                .introduced(computerMapper.stringToLocalDate(pComputerDto.getIntroduced()))
                .id(pComputerDto.getId()).manufacturer(company).build();
        computer.setId(pComputerDto.getId());
        computerRepository.save(computer);
        return ComputerAndDtoMapper.computerToDto(computer);
    }

    /**
     * Calls the delete() method on all the ID in the parameter List.
     * 
     * @param ids A list of integers representing the Ids to delete.
     */
    @Transactional
    public void deleteMultiplesId(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }
}