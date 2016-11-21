package com.excilys.formation.persistence.companydao;

import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.persistence.BaseDao;

public interface CompanyDao extends BaseDao<Company>{

    Company getById(int pId) throws PersistenceException;
}
