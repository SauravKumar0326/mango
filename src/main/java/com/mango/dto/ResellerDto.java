package com.mango.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mango.entities.Reseller;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResellerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4279860281037948173L;

	private long id;

	private String resellerName;

	private String resellerAddress;

	private String resellerPhone;

	private long resellerOpeningBalance;

	private long resellerClosingBalance;

	public ResellerDto(){
		super();
	}
	
	public ResellerDto(Reseller reseller) {
		super();
		this.id = reseller.getId();
		this.resellerName = reseller.getResellerName();
		this.resellerAddress = reseller.getResellerAddress();
		this.resellerPhone = reseller.getResellerPhone();
		this.resellerOpeningBalance = reseller.getResellerOpeningBalance();
		this.resellerClosingBalance = reseller.getResellerClosingBalance();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResellerName() {
		return resellerName;
	}

	public void setResellerName(String resellerName) {
		this.resellerName = resellerName;
	}

	public String getResellerAddress() {
		return resellerAddress;
	}

	public void setResellerAddress(String resellerAddress) {
		this.resellerAddress = resellerAddress;
	}

	public String getResellerPhone() {
		return resellerPhone;
	}

	public void setResellerPhone(String resellerPhone) {
		this.resellerPhone = resellerPhone;
	}

	public long getResellerOpeningBalance() {
		return resellerOpeningBalance;
	}

	public void setResellerOpeningBalance(long resellerOpeningBalance) {
		this.resellerOpeningBalance = resellerOpeningBalance;
	}

	public long getResellerClosingBalance() {
		return resellerClosingBalance;
	}

	public void setResellerClosingBalance(long resellerClosingBalance) {
		this.resellerClosingBalance = resellerClosingBalance;
	}

}
