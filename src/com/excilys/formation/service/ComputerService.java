package com.excilys.formation.service;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.service.exception.ServiceException;

public interface ComputerService extends BaseService<Computer> {

	default public Computer getByName (String s) throws ServiceException {
		throw new UnsupportedOperationException("getByName() is not implemented");
	}
}
