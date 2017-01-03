package com.excilys.formation.persistence.computerdao.computerdaoimpl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.persistence.computerdao.ComputerDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long>, ComputerDao {

    List<Computer> findAllByNameLike (String name);
    Integer countByName (String name);
    void deleteById(long id);
    void deleteById(Computer entity);
    Computer findByName(String name);
    Computer findById(long id);
    void deleteByCompanyId(long companyId);     
    
}