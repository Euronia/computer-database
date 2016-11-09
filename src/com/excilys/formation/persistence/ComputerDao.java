package com.excilys.formation.persistence;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.persistence.exception.PersistenceException;

public interface ComputerDao extends BaseDao<Computer>{

	default public Computer getByName (String s) throws PersistenceException {
		throw new UnsupportedOperationException("getByName() is not implemented");
	}
}
