package com.excilys.formation.persistence;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;

public interface BaseDao<T> {

    default T create(T t) throws PersistenceException {
        throw new UnsupportedOperationException("Create() is not implemented");
    }

    default Page<T> getPage(Page<T> ppage) throws PersistenceException {
        throw new UnsupportedOperationException("getList() is not implemented");
    }

    default T update(T t) throws PersistenceException {
        throw new UnsupportedOperationException("update() is not implemented");
    }

    default void delete(long companyId) throws PersistenceException {
        throw new UnsupportedOperationException("delete() is not implemented");
    }
}
