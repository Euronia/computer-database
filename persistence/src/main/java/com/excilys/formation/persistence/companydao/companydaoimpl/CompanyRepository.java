package com.excilys.formation.persistence.companydao.companydaoimpl;

import com.excilys.formation.entity.Company;
import com.excilys.formation.persistence.companydao.CompanyDao;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "company", path = "company")
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyDao {

    void deleteById(long id);
}