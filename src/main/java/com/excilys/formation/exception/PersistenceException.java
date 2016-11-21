package com.excilys.formation.exception;

public class PersistenceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8142084575525902279L;

	public PersistenceException() {
		super(); 
	}
	
	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public PersistenceException(Throwable cause){
		super(cause);
	}
	

}
