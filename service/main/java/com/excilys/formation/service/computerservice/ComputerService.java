package com.excilys.formation.service.computerservice;

import java.util.List;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.dto.PageConstraints;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.BaseService;

public interface ComputerService extends BaseService<ComputerDto> {

    public default ComputerDto getByName(String s) throws ServiceException {
        throw new UnsupportedOperationException("getByName() is not implemented");
    }

    public default ComputerDto getById(long l) throws ServiceException {
        throw new UnsupportedOperationException("getById() is not implemented");
    }

    public default void deleteMultiplesId(List<Long> ids) throws ServiceException {
        throw new UnsupportedOperationException("deleteMultiplesId() is not implemented");
    }

    public default Page<ComputerDto> getPage(PageConstraints constraints) {
        throw new UnsupportedOperationException("getPage() is not implemented");
    }
}