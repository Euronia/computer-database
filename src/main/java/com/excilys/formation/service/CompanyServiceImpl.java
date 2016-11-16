package com.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.excilys.formation.persistence.CompanyDaoImpl;
import com.excilys.formation.persistence.exception.PersistenceException;
import com.excilys.formation.service.util.ServiceUtil;

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
    public Page<CompanyDto> getPage(Page<CompanyDto> pPage) {
        Page<Company> pageCompany = new Page<Company>(10);
        ServiceUtil.copyAttributes(pPage, pageCompany);
        pageCompany.elements = dtoListToCompanyList(pPage.elements);
        try {
            pageCompany = companyDao.getPage(pageCompany);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        ServiceUtil.copyAttributes(pageCompany, pPage);
        pPage.elements = companyListToDtoList(pageCompany.elements);
        return pPage;
    }
    /**
     * Converts a list from CompanyDto to Company.
     * @param pListDto the list to convert
     * @return a Company List
     */
    private List<Company> dtoListToCompanyList(List<CompanyDto> pListDto) {
        List<Company> companies = null;
        if (pListDto != null) {
            companies = new ArrayList<>();
            for (CompanyDto company : pListDto) {
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
