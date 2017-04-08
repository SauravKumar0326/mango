package com.mango.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mango.dao.DtoEntityConverter;
import com.mango.dao.SupplierReportDao;
import com.mango.dto.ListResponse;
import com.mango.dto.SupplierReportDto;
import com.mango.entities.Company;
import com.mango.entities.Supplier;
import com.mango.entities.SupplierReport;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

@Repository
@Transactional
public class SupplierReportDaoImpl extends JPATemplate<SupplierReport>
		implements DtoEntityConverter<SupplierReportDto, SupplierReport>, SupplierReportDao {

	protected SupplierReportDaoImpl() {
		super(SupplierReport.class);
	}

	@Override
	public SupplierReportDto create(long companyId, SupplierReportDto supplierReportDto) throws DAOException {
		SupplierReport supplierReport = new SupplierReport();
		Company company = find(companyId, Company.class);
		Supplier supplier = find(supplierReportDto.getSupplierId(), Supplier.class);
		supplierReport.setCompanyId(company.getId());
		supplierReport.setSupplier(supplier);
		this.dtoToEntityConvert(supplierReportDto, supplierReport);
		save(supplierReport);
		supplierReportDto = new SupplierReportDto(supplierReport);
		return supplierReportDto;
	}

	@Override
	public SupplierReportDto update(long companyId, SupplierReportDto supplierReportDto, long supplierReportId)
			throws DAOException {
		SupplierReport supplierReport = find(supplierReportId, SupplierReport.class);
		Company company = find(companyId, Company.class);
		Supplier supplier = find(supplierReportDto.getSupplierId(), Supplier.class);
		supplierReport.setCompanyId(company.getId());
		supplierReport.setSupplier(supplier);
		this.dtoToEntityConvert(supplierReportDto, supplierReport);
		supplierReportDto = new SupplierReportDto(supplierReport);
		return supplierReportDto;

	}

	@Override
	public void dtoToEntityConvert(SupplierReportDto dto, SupplierReport entity) throws DAOException {
		entity.setAdvanceAmount(dto.getAdvanceAmount());
		entity.setAlottedQuantity(dto.getAlottedQuantity());
		entity.setBankCharges(dto.getBankCharges());
		entity.setComissionAndVitawa(dto.getComissionAndVitawa());
		entity.setDate(dto.getDate());
		entity.setFreight(dto.getFreight());
		entity.setFreightLineTotal(dto.getFreightLineTotal());
		entity.setInvoiceNumber(dto.getInvoiceNumber());
		entity.setItemName(dto.getItemName());
		entity.setLineTotal(dto.getLineTotal());
		entity.setOtherCharges(dto.getOtherCharges());
		entity.setPackingDate(dto.getPackingDate());
		entity.setPackingNumber(dto.getPackingNumber());
		entity.setPayTotal(dto.getPayTotal());
		entity.setPostage(dto.getPostage());
		entity.setRate(dto.getRate());
		entity.setSumTotal(dto.getSumTotal());
		entity.setTotalFreight(dto.getTotalFreight());
		entity.setTotalQuantity(dto.getTotalQuantity());
		entity.setTrademark(dto.getTrademark());
		entity.setUnloadingCharge(dto.getUnloadingCharge());
		entity.setUnloadingChargeLineTotal(dto.getUnloadingChargeLineTotal());

	}

	@Override
	protected Predicate getTenantPredicate(long companyId, CriteriaBuilder cb, Root<SupplierReport> root) {
		return cb.equal(root.get("companyId"), companyId);
	}

	@Override
	public ListResponse<SupplierReportDto> get(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws BadRequestException, DAOException {
		ListResponse<SupplierReportDto> supplierReportDtoList = null;
		List<SupplierReport> supplierReports = null;
		List<SupplierReportDto> supplierReportDtos = new ArrayList<SupplierReportDto>();
		supplierReports = findAll(companyId, start, count, filter, sortBy, sortOrder);
		if (supplierReports != null) {
			for (SupplierReport supplierReport : supplierReports) {
				supplierReportDtos.add(new SupplierReportDto(supplierReport));
			}
		}
		supplierReportDtoList = new ListResponse<SupplierReportDto>(getCount(filter, companyId), count, start,
				supplierReportDtos);
		return supplierReportDtoList;
	}

	/**
	 * Returns count for the given count query and filter
	 * 
	 * @param filter
	 * @param tenantId
	 * @return count
	 * @throws DAOException
	 */
	public Long getCount(String filter, long companyId) throws DAOException {
		try {
			return super.countAll(filter, companyId);
		} catch (DAOException e) {
			throw e;
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(long supplierReportId) throws DAOException {
		SupplierReport supplierReport = find(supplierReportId, SupplierReport.class);
		delete(supplierReport);
	}
}
