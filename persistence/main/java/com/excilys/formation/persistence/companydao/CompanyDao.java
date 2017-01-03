package com.excilys.formation.persistence.companydao;

import java.sql.Connection;

import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.persistence.BaseDao;

public interface CompanyDao extends BaseDao<Company> {

    public Company getById(int pId) throws PersistenceException;

    public default void delete(long companyId, Connection connection) throws PersistenceException {
        throw new UnsupportedOperationException("delete(long, Connection) is not implemented");
    }
}