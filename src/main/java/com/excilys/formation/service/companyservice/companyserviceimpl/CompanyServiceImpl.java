package com.excilys.formation.service.companyservice.companyserviceimpl;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.CompanyAndDtoMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.companydao.CompanyDao;
import com.excilys.formation.persistence.companydao.companydaoimpl.CompanyDaoImpl;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import com.excilys.formation.persistence.computerdao.computerdaoimpl.ComputerDaoImpl;
import com.excilys.formation.persistence.connectionprovider.HikariConnectionProvider;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.util.ServiceUtil;

@Service
public class CompanyServiceImpl implements CompanyService {

    ////////// Parameters //////////

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ComputerDao computerDao;
    
    private static Logger logger;
    
    static{
        logger = (Logger) LoggerFactory.getLogger("cdbLogger");
    }

    ////////// Constructors //////////
    
    /**
     * Constructor for CompanyServiceImpl.
     * Initializes the companyDao.
     */
     public CompanyServiceImpl() {
        companyDao = CompanyDaoImpl.getInstance();
    }

    ////////// Methods ///////////

    @Override
    public Page<CompanyDto> getPage(Page<CompanyDto> page) {
        Page<Company> pageCompany = new Page<Company>(10);
        ServiceUtil.copyAttributes(page, pageCompany);
        pageCompany.elements = CompanyAndDtoMapper.dtoListToCompanyList(page.elements);
        try {
            pageCompany = companyDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            logger.error("CompanyServiceImpl : getPage(Page<ComputerDto> catched PersistenceException ");
            logger.error(e.getStackTrace().toString());
        }
        ServiceUtil.copyAttributes(pageCompany, page);
        page.elements = CompanyAndDtoMapper.companyListToDtoList(pageCompany.elements);
        return page;
    }
    
    @Override
    public void deleteCompany (long companyId) {
        HikariConnectionProvider connectionProvider = HikariConnectionProvider.getInstance();
        try {
            connectionProvider.beginTransaction();
            computerDao.deleteFromCompany(companyId, connectionProvider.getTransactionConnection());
            companyDao.delete(companyId, connectionProvider.getTransactionConnection());
            connectionProvider.commitTransaction();
        } catch (PersistenceException e) {
            logger.error( "CompanyServiceImpl : deleteCompany() catched PersistenceException",e);
            connectionProvider.rollbackTransaction();
        }
        
    }
    
  
}