package com.excilys.formation.service;

import java.util.List;

import com.excilys.formation.service.exception.ServiceException;

public interface BaseService<T> {
	
	default public T create (T t) throws ServiceException {
		throw new UnsupportedOperationException("Create() is not implemented");
	}
	default public List<T> getList() throws ServiceException {
		throw new UnsupportedOperationException("getList() is not implemented");
	}
	default public T update (T t) throws ServiceException {
		throw new UnsupportedOperationException("update() is not implemented");
	}
	default public void delete (T t) throws ServiceException {
		throw new UnsupportedOperationException("delete() is not implemented");
	}
}
