package com.mango.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7277595803634018232L;

	/**
	 * @param description
	 */
	public ErrorResponse(String description) {
		super();
		this.description = description;
	}

	/**
	 * This will represent the error description
	 */
	private String description;

	/**
	 * @return the errorDescr
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param errorDescr
	 *            the error description to set
	 */
	public void setDescription(String errorDescr) {
		this.description = errorDescr;
	}
}
