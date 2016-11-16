package com.excilys.formation.persistence;

import com.excilys.formation.entity.Company;
import com.excilys.formation.persistence.exception.PersistenceException;

public interface CompanyDao extends BaseDao<Company>{

    Company getById(int pId) throws PersistenceException;
}
