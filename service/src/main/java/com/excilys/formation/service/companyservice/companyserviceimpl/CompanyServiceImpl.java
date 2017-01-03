package com.excilys.formation.service.companyservice.companyserviceimpl;

import ch.qos.logback.classic.Logger;
import com.excilys.formation.persistence.companydao.companydaoimpl.CompanyRepository;
import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.CompanyAndDtoMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.companyservice.CompanyService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {

    ////////// Parameters //////////

    @Autowired
    private CompanyRepository companyDao;

    @Autowired
    private ComputerRepository computerRepository;

    private static Logger logger;

    static {
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
    }

    ////////// Methods ///////////
    
    @Override
    public Page<CompanyDto> getAll() {
        Page<CompanyDto> returnPage = new Page<>(10);
        returnPage.setElementsByPage(10);
        returnPage.setElements(CompanyAndDtoMapper.companyListToDtoList(companyDao.findAll()));
        returnPage.setElementsByPage(returnPage.getTotalElements());
        return returnPage;
    }

    @Transactional
    @Override
    public void deleteCompany(long companyId) {
        try {
            computerRepository.deleteByCompanyId(companyId);
            companyDao.delete(companyId);
        } catch (PersistenceException e) {
            logger.error("CompanyServiceImpl : deleteCompany() catched PersistenceException", e);
        }

    }

}