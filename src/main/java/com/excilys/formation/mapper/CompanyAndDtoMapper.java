package com.excilys.formation.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;

public class CompanyAndDtoMapper {

    /**
     * Converts a list from CompanyDto to Company.
     * @param listDto the list to convert
     * @return a Company List
     */
    public static List<Company> dtoListToCompanyList(List<CompanyDto> listDto) {
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
    public static List<CompanyDto> companyListToDtoList(List<Company> pList) {
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
