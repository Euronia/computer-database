package com.excilys.formation.service.companyservice.companyserviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.CompanyAndDtoMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.companydao.CompanyDao;
import com.excilys.formation.persistence.companydao.companydaoimpl.CompanyDaoImpl;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.util.ServiceUtil;

public class CompanyServiceImpl implements CompanyService {

    ////////// Parameters //////////

    private CompanyDao companyDao;
    private static Logger logger;
    
    static{
        logger = LoggerFactory.getLogger("cdbLogger");
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
    
  
}