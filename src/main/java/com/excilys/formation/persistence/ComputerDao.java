package com.excilys.formation.persistence;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.persistence.exception.PersistenceException;

public interface ComputerDao extends BaseDao<Computer> {

    default public Computer getByName(String s) throws PersistenceException {
        throw new UnsupportedOperationException("getByName() is not implemented");
    }
    
    default public Computer getById(int pId) throws PersistenceException {
        throw new UnsupportedOperationException("getById() is not implemented");
    }

    default public void delete(Computer pcomputer) throws PersistenceException {
        throw new UnsupportedOperationException("delete() is not implemented");
    }
}