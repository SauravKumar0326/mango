package com.mango.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mango.entities.ResellerReport;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResellerReportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -147998804767666016L;

	private long id;

	private long resellerId;

	private String resellerName;

	private long companyId;

	private Date dateFrom;

	private Date dateTo;

	private Date date;

	private String trademark;

	private String itemName;

	private Date packingDate;

	private String packingNumber;

	private long alottedQuantity;

	private long rate;

	private long lineTotal;

	private long totalQuantity;

	private long sumTotal;

	private long amountReceived;

	private long amountRoundedOff;

	private long closingBalance;

	public ResellerReportDto() {
		super();
	};

	public ResellerReportDto(ResellerReport resellerReport) {
		super();
		this.id = resellerReport.getId();
		this.resellerId = resellerReport.getReseller().getId();
		this.companyId = resellerReport.getCompanyId();
		this.resellerName = resellerReport.getReseller().getResellerName();
		this.alottedQuantity = resellerReport.getAlottedQuantity();
		this.amountReceived = resellerReport.getAmountReceived();
		this.amountRoundedOff = resellerReport.getAmountRoundedOff();
		this.closingBalance = resellerReport.getClosingBalance();
		this.date = resellerReport.getDate();
		this.dateFrom = resellerReport.getDateFrom();
		this.dateTo = resellerReport.getDateTo();
		this.itemName = resellerReport.getItemName();
		this.lineTotal = resellerReport.getLineTotal();
		this.packingDate = resellerReport.getPackingDate();
		this.packingNumber = resellerReport.getPackingNumber();
		this.rate = resellerReport.getRate();
		this.sumTotal = resellerReport.getSumTotal();
		this.totalQuantity = resellerReport.getTotalQuantity();
		this.trademark = resellerReport.getTrademark();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getResellerId() {
		return resellerId;
	}

	public void setResellerId(long resellerId) {
		this.resellerId = resellerId;
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

	public String getResellerName() {
		return resellerName;
	}

	public void setResellerName(String resellerName) {
		this.resellerName = resellerName;
	}
}
