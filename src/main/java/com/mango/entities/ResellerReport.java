package com.mango.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "resellerReport")
public class ResellerReport extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376540367171305698L;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Reseller.class)
	@JoinColumn(name = "reseller_id", referencedColumnName = "id")
	private Reseller reseller;

	@Column
	private long companyId;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column
	private Date dateTo;

	@Temporal(TemporalType.DATE)
	@Column
	private Date date;

	@Column
	private String trademark;

	@Column
	private String itemName;

	@Temporal(TemporalType.DATE)
	@Column
	private Date packingDate;

	@Column
	private String packingNumber;

	@Column
	private long alottedQuantity;

	@Column
	private long rate;

	@Column
	private long lineTotal;

	@Column
	private long totalQuantity;

	@Column
	private long sumTotal;

	@Column
	private long amountReceived;

	@Column
	private long amountRoundedOff;

	@Column
	private long closingBalance;

	public Reseller getReseller() {
		return reseller;
	}

	public void setReseller(Reseller reseller) {
		this.reseller = reseller;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getPackingDate() {
		return packingDate;
	}

	public void setPackingDate(Date packingDate) {
		this.packingDate = packingDate;
	}

	public String getPackingNumber() {
		return packingNumber;
	}

	public void setPackingNumber(String packingNumber) {
		this.packingNumber = packingNumber;
	}

	public long getAlottedQuantity() {
		return alottedQuantity;
	}

	public void setAlottedQuantity(long alottedQuantity) {
		this.alottedQuantity = alottedQuantity;
	}

	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	public long getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(long lineTotal) {
		this.lineTotal = lineTotal;
	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public long getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(long sumTotal) {
		this.sumTotal = sumTotal;
	}

	public long getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(long amountReceived) {
		this.amountReceived = amountReceived;
	}

	public long getAmountRoundedOff() {
		return amountRoundedOff;
	}

	public void setAmountRoundedOff(long amountRoundedOff) {
		this.amountRoundedOff = amountRoundedOff;
	}

	public long getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(long closingBalance) {
		this.closingBalance = closingBalance;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

}
