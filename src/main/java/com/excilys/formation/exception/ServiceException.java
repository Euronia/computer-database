package com.excilys.formation.exception;

public class ServiceException extends Exception {
	
	/**
	 * The encountered exception when using the service's classes 
	 */
	private static final long serialVersionUID = 4107543775695274188L;

	public ServiceException() {
		super(); 
	}
	
	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public ServiceException(Throwable cause){
		super(cause);
	}
	

}

