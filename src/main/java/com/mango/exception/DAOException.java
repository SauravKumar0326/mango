
package com.mango.exception;

public class DAOException extends MangoException {

	/**
	 *
	 */
	private static final long serialVersionUID = -2137553429285272182L;

	/**
	 * DAO Exception.
	 */
	public static final String DB_EXCEPTION = "Exception occurred in DB. Reason: ";

	/**
	 *
	 */
	public DAOException() {
		super(DB_EXCEPTION);
	}

	/**
	 * @param message
	 */
	public DAOException(String message) {
		super(DB_EXCEPTION + message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
