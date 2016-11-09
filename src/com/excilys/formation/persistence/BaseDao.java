package com.excilys.formation.persistence;

import java.util.List;

import com.excilys.formation.persistence.exception.PersistenceException;

public interface BaseDao<T> {
	
	default public T create (T t) throws PersistenceException {
		throw new UnsupportedOperationException("Create() is not implemented");
	}
	default public List<T> getList() throws PersistenceException {
		throw new UnsupportedOperationException("getList() is not implemented");
	}
	default public T update (T t) throws PersistenceException {
		throw new UnsupportedOperationException("update() is not implemented");
	}
	default public void delete (T t) throws PersistenceException {
		throw new UnsupportedOperationException("delete() is not implemented");
	}
}
