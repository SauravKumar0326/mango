package com.mango.exception;

import com.mango.utils.Constants;

public class BadRequestException extends MangoException {

	private static final long serialVersionUID = -6726723958342742766L;

	/**
	 * No-arg constructor
	 */
	public BadRequestException() {
		super(Constants.INVALID_REQUEST);
	}

	/**
	 * Constructor for customizing message
	 * 
	 * @param message
	 *            new message
	 */
	public BadRequestException(String message) {
		super(message);
	}
}
