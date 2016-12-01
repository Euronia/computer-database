package com.excilys.formation.persistence.computerdao;

import java.sql.Connection;

import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.BaseDao;

public interface ComputerDao extends BaseDao<Computer> {

    public default Computer getByName(String s) throws PersistenceException {
        throw new UnsupportedOperationException("getByName() is not implemented");
    }
    
    public default Computer getById(long pId) throws PersistenceException {
        throw new UnsupportedOperationException("getById() is not implemented");
    }

    public default void delete(Computer pcomputer) throws PersistenceException {
        throw new UnsupportedOperationException("delete(Computer) is not implemented");
    }
    
    public default void delete(long id) throws PersistenceException {
        throw new UnsupportedOperationException("delete(long) is not implemented");
    }

    public default void deleteFromCompany(Long companyId, Connection connection) {
        throw new UnsupportedOperationException("deleteFromCompany() is not implemented");
    }
    
    public default Page<Computer> getPage(Page<Computer> page, PageConstraints constraints) throws PersistenceException {
        throw new UnsupportedOperationException("getPage() is not implemented");
    }
}