package com.mango.dto;

import java.io.Serializable;

public class CompanyDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8878151452245111046L;

	private long supplierCount;

	private long resellerCount;

	private long supplierReportCount;

	private long resellerReportCount;

	public CompanyDetail() {
		super();
		this.resellerCount = 0;
		this.resellerCount = 0;
		this.resellerReportCount = 0;
		this.supplierReportCount = 0;
	}

	public long getSupplierCount() {
		return supplierCount;
	}

	public void setSupplierCount(long supplierCount) {
		this.supplierCount = supplierCount;
	}

	public long getResellerCount() {
		return resellerCount;
	}

	public void setResellerCount(long resellerCount) {
		this.resellerCount = resellerCount;
	}

	public long getSupplierReportCount() {
		return supplierReportCount;
	}

	public void setSupplierReportCount(long supplierReportCount) {
		this.supplierReportCount = supplierReportCount;
	}

	public long getResellerReportCount() {
		return resellerReportCount;
	}

	public void setResellerReportCount(long resellerReportCount) {
		this.resellerReportCount = resellerReportCount;
	}

}
