package com.excilys.formation.persistence;

import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.exception.PersistenceException;

public interface BaseDao<T> {
	
	default public T create (T t) throws PersistenceException {
		throw new UnsupportedOperationException("Create() is not implemented");
	}
	default public Page<T> getPage(Page<T> ppage) throws PersistenceException {
		throw new UnsupportedOperationException("getList() is not implemented");
	}
	default public T update (T t) throws PersistenceException {
		throw new UnsupportedOperationException("update() is not implemented");
	}
	default public void delete (int pId) throws PersistenceException {
		throw new UnsupportedOperationException("delete() is not implemented");
	}
}
