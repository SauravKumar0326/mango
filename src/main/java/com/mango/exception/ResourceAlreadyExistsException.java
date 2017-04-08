package com.mango.exception;

import static com.mango.utils.Constants.RESOURCE_ALREADY_EXISTS;

public class ResourceAlreadyExistsException extends MangoException {

	private static final long serialVersionUID = -813537127913756846L;

	private Object entity;

	/**
	 * No-arg constructor
	 */
	public ResourceAlreadyExistsException() {
		this(RESOURCE_ALREADY_EXISTS);
	}

	/**
	 * Constructor for customizing message
	 * 
	 * @param message
	 *            new message
	 */
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

	/**
	 * @param entity
	 *            entity that exists in the system
	 */
	public ResourceAlreadyExistsException(Object entity) {
		this();
		this.entity = entity;
	}

	/**
	 * Constructor for customizing message
	 * 
	 * @param message
	 *            new message
	 * @param entity
	 *            entity that exists in the system
	 */
	public ResourceAlreadyExistsException(String message, Object entity) {
		this(message);
		this.entity = entity;
	}

	/**
	 * @return the entity
	 */
	public Object getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(Object entity) {
		this.entity = entity;
	}

}
