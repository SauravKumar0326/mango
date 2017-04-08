package com.mango.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
@NamedQueries({
		@NamedQuery(name = Company.COUNT_COMPANY_BY_EMAIL, query = "SELECT COUNT(o) FROM Company o where o.email =:email"),
		@NamedQuery(name = Company.GET_COMPANY_BY_EMAIL, query = "SELECT o FROM Company o where o.email =:email"),
		@NamedQuery(name = Company.GET_COMPANY_BY_TOKEN, query = "SELECT o FROM Company o where o.token =:token") })
public class Company extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -304295542288096123L;

	public static final String COUNT_COMPANY_BY_EMAIL = "getCompanyCountByEmail";

	public static final String GET_COMPANY_BY_EMAIL = "getCompanyByEmail";

	public static final String GET_COMPANY_BY_TOKEN = "getCompanyByToken";

	@Column
	private String companyName;

	@Column(columnDefinition = "MEDIUMTEXT")
	private String companyAddress;

	@Column
	private String companyPhone;

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String token;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REMOVE })
	private Collection<Reseller> resellers;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REMOVE })
	private Collection<Supplier> supplier;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public Collection<Reseller> getResellers() {
		return resellers;
	}

	public void setResellers(Collection<Reseller> resellers) {
		this.resellers = resellers;
	}

	public Collection<Supplier> getSupplier() {
		return supplier;
	}

	public void setSupplier(Collection<Supplier> supplier) {
		this.supplier = supplier;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
