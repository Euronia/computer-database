package com.excilys.formation.service.computerservice;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.service.BaseService;

public interface ComputerService extends BaseService<ComputerDto> {

	default public ComputerDto getByName (String s) throws ServiceException {
		throw new UnsupportedOperationException("getByName() is not implemented");
	}

    default public ComputerDto getById(int pId) throws ServiceException {
        throw new UnsupportedOperationException("getById() is not implemented");
    };
}
