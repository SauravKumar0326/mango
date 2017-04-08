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
@Table(name = "reseller")
public class Reseller extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1720217518068552485L;

	@Column
	private String resellerName;

	@Column(columnDefinition = "MEDIUMTEXT")
	private String resellerAddress;

	@Column
	private String resellerPhone;

	@Column
	private long resellerOpeningBalance;

	@Column
	private long resellerClosingBalance;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Company.class)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	@OneToMany(mappedBy = "reseller", fetch = FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private Collection<ResellerReport> resellerReport;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Collection<ResellerReport> getResellerReport() {
		return resellerReport;
	}

	public void setResellerReport(Collection<ResellerReport> resellerReport) {
		this.resellerReport = resellerReport;
	}

}
