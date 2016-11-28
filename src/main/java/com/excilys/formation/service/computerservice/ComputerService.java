package com.excilys.formation.service.computerservice;

import java.util.List;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.entity.Computer;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.BaseService;

public interface ComputerService extends BaseService<ComputerDto> {

	default public ComputerDto getByName (String s) throws ServiceException {
		throw new UnsupportedOperationException("getByName() is not implemented");
	}

    default public ComputerDto getById(int pId) throws ServiceException {
        throw new UnsupportedOperationException("getById() is not implemented");
    }
    
    default public void deleteMultiplesId (List<Integer> ids) throws ServiceException {
        throw new UnsupportedOperationException("deleteMultiplesId() is not implemented");
    }
    
    default public Page<Computer> getPage(PageConstraints constraints) {
        throw new UnsupportedOperationException("getPage() is not implemented");
    }
}
