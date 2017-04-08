package com.mango.dto;

import java.io.Serializable;

public class SignInDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9190749671374936635L;

	private String email;

	private String password;

	public SignInDto() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
