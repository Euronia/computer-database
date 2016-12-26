package com.excilys.formation.service.companyservice;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.BaseService;

public interface CompanyService extends BaseService<CompanyDto> {

    void deleteCompany(long CompanyId);
    
    Page<CompanyDto> getAll ();

}