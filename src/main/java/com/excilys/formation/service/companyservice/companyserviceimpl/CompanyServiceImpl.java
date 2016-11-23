package com.excilys.formation.service.companyservice.companyserviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.companydao.CompanyDao;
import com.excilys.formation.persistence.companydao.companydaoimpl.CompanyDaoImpl;
import com.excilys.formation.service.companyservice.CompanyService;
import com.excilys.formation.util.ServiceUtil;

public class CompanyServiceImpl implements CompanyService {
    private CompanyDao companyDao;
    /**
     * Constructor for CompanyServiceImpl.
     * Initializes the companyDao.
     */
    
    public CompanyServiceImpl() {
        companyDao = CompanyDaoImpl.getInstance();
    }
    @Override
    public Page<CompanyDto> getPage(Page<CompanyDto> page) {
        Page<Company> pageCompany = new Page<Company>(10);
        ServiceUtil.copyAttributes(page, pageCompany);
        pageCompany.elements = dtoListToCompanyList(page.elements);
        try {
            pageCompany = companyDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ServiceUtil.copyAttributes(pageCompany, page);
        page.elements = companyListToDtoList(pageCompany.elements);
        return page;
    }
    /**
     * Converts a list from CompanyDto to Company.
     * @param listDto the list to convert
     * @return a Company List
     */
    private List<Company> dtoListToCompanyList(List<CompanyDto> listDto) {
        List<Company> companies = null;
        if (listDto != null) {
            companies = new ArrayList<>();
            for (CompanyDto company : listDto) {
                Company companyToAdd = new Company(company.name);
                companyToAdd.setId(company.id);
                companies.add(companyToAdd);
            }
        }
        return companies;
    }
    /**
     * Convert a list from Company to CompanyDto.
     * @param pList the list to convert
     * @return a CompanyDto List
     */
    private List<CompanyDto> companyListToDtoList(List<Company> pList) {
        List<CompanyDto> companiesDto = null;
        if (pList != null) {
            companiesDto = new ArrayList<>();
            for (Company company : pList) {
                CompanyDto companyDto = new CompanyDto();
                companyDto.id = company.getId();
                companyDto.name = company.getName();
                companiesDto.add(companyDto);
            }
        }
        return companiesDto;
    }
}
