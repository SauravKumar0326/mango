package com.mango.exception;

public class MangoException extends Exception {

	private static final long serialVersionUID = -97489601876868146L;

	/**
	 * 
	 * @param errorMessage
	 */
	public MangoException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * 
	 * @param errorMessage
	 * @param cause
	 */
	public MangoException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	/**
	 * Default Constructor
	 */
	public MangoException() {
		super();
	}
}
