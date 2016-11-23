package com.excilys.formation.persistence;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.pagination.Page;

public interface BaseDao<T> {
	
	public default T create (T t) throws PersistenceException {
		throw new UnsupportedOperationException("Create() is not implemented");
	}
	public default Page<T> getPage(Page<T> ppage) throws PersistenceException {
		throw new UnsupportedOperationException("getList() is not implemented");
	}
	public default T update (T t) throws PersistenceException {
		throw new UnsupportedOperationException("update() is not implemented");
	}
	public default void delete (int pId) throws PersistenceException {
		throw new UnsupportedOperationException("delete() is not implemented");
	}
}
