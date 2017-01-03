package com.excilys.formation.persistence.companydao.companydaoimpl;

import com.excilys.formation.entity.Company;
import com.excilys.formation.persistence.companydao.CompanyDao;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyDao {

    void deleteById(long id);
    void deleteById(Company entity);
}