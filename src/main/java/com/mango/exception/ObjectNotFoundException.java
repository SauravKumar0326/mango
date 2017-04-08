/**
 * 
 */
package com.mango.exception;

import static com.mango.utils.Constants.RESOURCE_NOT_FOUND;

public class ObjectNotFoundException extends DAOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 */
	public ObjectNotFoundException() {
		this(RESOURCE_NOT_FOUND);
	}

	/**
	 * Error message string for the resource where conflict happens.
	 * 
	 * @param message
	 */
	public ObjectNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}