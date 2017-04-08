package com.mango.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5557806696030078335L;

	@Column
	private String supplierName;

	@Column(columnDefinition = "MEDIUMTEXT")
	private String supplierAddress;

	@Column
	private String supplierPhone;

	@Column
	private long supplierOpeningBalance;

	@Column
	private long SupplierAdvanceAmount;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Company.class)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REMOVE })
	private Collection<SupplierReport> supplierReport;

	public Collection<SupplierReport> getSupplierReport() {
		return supplierReport;
	}

	public void setSupplierReport(Collection<SupplierReport> supplierReport) {
		this.supplierReport = supplierReport;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
