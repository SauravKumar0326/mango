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
@Table(name = "supplierReport")
public class SupplierReport extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -375028774270073554L;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Supplier.class)
	@JoinColumn(name = "supplier_id", referencedColumnName = "id")
	private Supplier supplier;

	@Column
	private long companyId;

	@Temporal(TemporalType.DATE)
	@Column
	private Date date;

	@Column
	private long advanceAmount;

	@Column
	private long invoiceNumber;

	@Column
	private String trademark;

	@Column
	private String itemName;

	@Column
	private long alottedQuantity;

	@Temporal(TemporalType.DATE)
	@Column
	private Date packingDate;

	@Column
	private String packingNumber;

	@Column
	private long rate;

	@Column
	private long lineTotal;

	@Column
	private long freight;

	@Column
	private long freightLineTotal;

	@Column
	private long unloadingCharge;

	@Column
	private long unloadingChargeLineTotal;

	@Column
	private long sumTotal;

	@Column
	private long comissionAndVitawa;

	@Column
	private String postage;

	@Column
	private long otherCharges;

	@Column
	private long bankCharges;

	@Column
	private long totalQuantity;

	@Column
	private long totalFreight;

	@Column
	private long payTotal;

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
