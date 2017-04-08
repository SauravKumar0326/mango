package com.mango.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mango.entities.SupplierReport;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierReportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9145538583704159699L;

	private long id;

	private long supplierId;

	private String supplierName;

	private long companyId;

	private Date date;

	private long advanceAmount;

	private long invoiceNumber;

	private String trademark;

	private String itemName;

	private long alottedQuantity;

	private Date packingDate;

	private String packingNumber;

	private long rate;

	private long lineTotal;

	private long freight;

	private long freightLineTotal;

	private long unloadingCharge;

	private long unloadingChargeLineTotal;

	private long sumTotal;

	private long comissionAndVitawa;

	private String postage;

	private long otherCharges;

	private long bankCharges;

	private long totalQuantity;

	private long totalFreight;

	private long payTotal;

	public SupplierReportDto() {
		super();
	}

	public SupplierReportDto(SupplierReport supplierReport) {
		super();
		this.id = supplierReport.getId();
		this.companyId = supplierReport.getCompanyId();
		this.supplierName = supplierReport.getSupplier().getSupplierName();
		this.supplierId = supplierReport.getSupplier().getId();
		this.advanceAmount = supplierReport.getAdvanceAmount();
		this.alottedQuantity = supplierReport.getAlottedQuantity();
		this.bankCharges = supplierReport.getBankCharges();
		this.comissionAndVitawa = supplierReport.getComissionAndVitawa();
		this.date = supplierReport.getDate();
		this.freight = supplierReport.getFreight();
		this.freightLineTotal = supplierReport.getFreightLineTotal();
		this.invoiceNumber = supplierReport.getInvoiceNumber();
		this.itemName = supplierReport.getItemName();
		this.lineTotal = supplierReport.getLineTotal();
		this.otherCharges = supplierReport.getOtherCharges();
		this.packingDate = supplierReport.getPackingDate();
		this.packingNumber = supplierReport.getPackingNumber();
		this.payTotal = supplierReport.getPayTotal();
		this.postage = supplierReport.getPostage();
		this.rate = supplierReport.getRate();
		this.sumTotal = supplierReport.getSumTotal();
		this.totalFreight = supplierReport.getTotalFreight();
		this.totalQuantity = supplierReport.getTotalQuantity();
		this.trademark = supplierReport.getTrademark();
		this.unloadingCharge = supplierReport.getUnloadingCharge();
		this.unloadingChargeLineTotal = supplierReport.getUnloadingChargeLineTotal();

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(long advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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

	public long getAlottedQuantity() {
		return alottedQuantity;
	}

	public void setAlottedQuantity(long alottedQuantity) {
		this.alottedQuantity = alottedQuantity;
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

	public long getFreight() {
		return freight;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

	public long getFreightLineTotal() {
		return freightLineTotal;
	}

	public void setFreightLineTotal(long freightLineTotal) {
		this.freightLineTotal = freightLineTotal;
	}

	public long getUnloadingCharge() {
		return unloadingCharge;
	}

	public void setUnloadingCharge(long unloadingCharge) {
		this.unloadingCharge = unloadingCharge;
	}

	public long getUnloadingChargeLineTotal() {
		return unloadingChargeLineTotal;
	}

	public void setUnloadingChargeLineTotal(long unloadingChargeLineTotal) {
		this.unloadingChargeLineTotal = unloadingChargeLineTotal;
	}

	public long getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(long sumTotal) {
		this.sumTotal = sumTotal;
	}

	public long getComissionAndVitawa() {
		return comissionAndVitawa;
	}

	public void setComissionAndVitawa(long comissionAndVitawa) {
		this.comissionAndVitawa = comissionAndVitawa;
	}

	public String getPostage() {
		return postage;
	}

	public void setPostage(String postage) {
		this.postage = postage;
	}

	public long getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(long otherCharges) {
		this.otherCharges = otherCharges;
	}

	public long getBankCharges() {
		return bankCharges;
	}

	public void setBankCharges(long bankCharges) {
		this.bankCharges = bankCharges;
	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public long getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(long totalFreight) {
		this.totalFreight = totalFreight;
	}

	public long getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(long payTotal) {
		this.payTotal = payTotal;
	}

}
