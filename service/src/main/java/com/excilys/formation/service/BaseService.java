package com.excilys.formation.service;

import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.pagination.Page;

public interface BaseService<T> {

    default public T create(T t) throws ServiceException {
        throw new UnsupportedOperationException("Create() is not implemented");
    }

    default public Page<T> getPage(Page<T> t) throws ServiceException {
        throw new UnsupportedOperationException("getPAge() is not implemented");
    }

    default public T update(T t) throws ServiceException {
        throw new UnsupportedOperationException("update() is not implemented");
    }

    default public void delete(long id) throws ServiceException {
        throw new UnsupportedOperationException("delete() is not implemented");
    }
}
