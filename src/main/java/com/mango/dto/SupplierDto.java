package com.mango.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mango.entities.Supplier;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8240213111579656322L;

	private long id;

	private String supplierName;

	private String supplierAddress;

	private String supplierPhone;

	private long supplierOpeningBalance;

	private long SupplierAdvanceAmount;

	public SupplierDto() {
		super();
	}

	public SupplierDto(Supplier supplier) {
		super();
		this.id = supplier.getId();
		this.supplierName = supplier.getSupplierName();
		this.supplierAddress = supplier.getSupplierAddress();
		this.supplierOpeningBalance = supplier.getSupplierOpeningBalance();
		this.supplierPhone = supplier.getSupplierPhone();
		this.SupplierAdvanceAmount = supplier.getSupplierAdvanceAmount();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public long getSupplierOpeningBalance() {
		return supplierOpeningBalance;
	}

	public void setSupplierOpeningBalance(long supplierOpeningBalance) {
		this.supplierOpeningBalance = supplierOpeningBalance;
	}

	public long getSupplierAdvanceAmount() {
		return SupplierAdvanceAmount;
	}

	public void setSupplierAdvanceAmount(long supplierAdvanceAmount) {
		SupplierAdvanceAmount = supplierAdvanceAmount;
	}

}
