package com.excilys.formation.service.computerservice.computerserviceimpl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Company;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.mapper.ConstraintMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerRepository;
import com.excilys.formation.service.computerservice.ComputerService;

import ch.qos.logback.classic.Logger;

@Service
public class ComputerServiceSpringmpl implements ComputerService {
    
    @Autowired
    ComputerRepository computerRepository;
    @Autowired
    private ComputerAndDtoMapper computerMapper;
    
    private static Logger logger;

    static {
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
    }
    
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

    @Override
    public void delete(long id) {
        try {
            computerRepository.delete(id);
        } catch (PersistenceException e) {
            logger.error("ComputerServiceImpl : delete(long) catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
    }

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
        PageRequest pageRequest = new PageRequest(pageComputer.currentPage-1,(int) pageComputer.elementsPerPage);
        /*org.springframework.data.domain.Page<Computer> listComputer = computerRepository.findAll(pageRequest);     
        pageComputer.setElements(listComputer.getContent());*/
        if (constraints.getFilter() != null) {
            System.out.println("filter");
            pageComputer.setElements(computerRepository.findAllByNameLike(constraints.getFilter()));
            System.out.println(pageComputer.getElements());
        } else {
            System.out.println("not filtered");
            pageComputer.setElements(computerRepository.findAll(pageRequest).getContent());
            System.out.println(pageComputer.getElements());
        }
            pageComputer.setTotalElements(computerRepository.count());
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
        computer.setId(pComputerDto.getId());
        computerRepository.save(computer);
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
