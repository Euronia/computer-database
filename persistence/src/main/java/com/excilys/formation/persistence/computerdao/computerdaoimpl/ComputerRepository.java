package com.excilys.formation.persistence.computerdao.computerdaoimpl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource (collectionResourceRel = "computer", path = "computer")
public interface ComputerRepository extends JpaRepository<Computer, Long>, ComputerDao {

    List<Computer> findByNameContaining (String name);
    Integer countByName (String name);
    void deleteById(long id);
    List<Computer> findByName(String name);
    Computer findById(long id);
    void deleteByCompanyId(long companyId);     
    
}