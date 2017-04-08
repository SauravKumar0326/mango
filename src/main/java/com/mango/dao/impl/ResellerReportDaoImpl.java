package com.mango.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mango.dao.DtoEntityConverter;
import com.mango.dao.ResellerReportDao;
import com.mango.dto.ListResponse;
import com.mango.dto.ResellerReportDto;
import com.mango.entities.Company;
import com.mango.entities.Reseller;
import com.mango.entities.ResellerReport;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

@Repository
@Transactional
public class ResellerReportDaoImpl extends JPATemplate<ResellerReport>
		implements DtoEntityConverter<ResellerReportDto, ResellerReport>, ResellerReportDao {

	protected ResellerReportDaoImpl() {
		super(ResellerReport.class);
	}

	@Override
	public ResellerReportDto create(long companyId, ResellerReportDto resellerReportDto) throws DAOException {
		ResellerReport resellerReport = new ResellerReport();
		Reseller reseller = find(resellerReportDto.getResellerId(), Reseller.class);
		Company company = find(companyId, Company.class);
		resellerReport.setCompanyId(company.getId());
		this.dtoToEntityConvert(resellerReportDto, resellerReport);
		resellerReport.setReseller(reseller);
		save(resellerReport);
		resellerReportDto = new ResellerReportDto(resellerReport);
		return resellerReportDto;
	}

	@Override
	public void dtoToEntityConvert(ResellerReportDto dto, ResellerReport entity) throws DAOException {

		entity.setAlottedQuantity(dto.getAlottedQuantity());
		entity.setAmountReceived(dto.getAmountReceived());
		entity.setAmountRoundedOff(dto.getAmountRoundedOff());
		entity.setClosingBalance(dto.getClosingBalance());
		entity.setDate(dto.getDate());
		entity.setDateFrom(dto.getDateFrom());
		entity.setDateTo(dto.getDateTo());
		entity.setItemName(dto.getItemName());
		entity.setLineTotal(dto.getLineTotal());
		entity.setPackingDate(dto.getPackingDate());
		entity.setPackingNumber(dto.getPackingNumber());
		entity.setRate(dto.getRate());
		entity.setSumTotal(dto.getSumTotal());
		entity.setTotalQuantity(dto.getTotalQuantity());
		entity.setTrademark(dto.getTrademark());
	}

	@Override
	protected Predicate getTenantPredicate(long companyId, CriteriaBuilder cb, Root<ResellerReport> root) {
		return cb.equal(root.get("companyId"), companyId);
	}

	@Override
	public ListResponse<ResellerReportDto> get(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws BadRequestException, DAOException {
		ListResponse<ResellerReportDto> resellerReportDtoList = null;
		List<ResellerReport> resellerReports = null;
		List<ResellerReportDto> resellerReportDtos = new ArrayList<ResellerReportDto>();
		resellerReports = findAll(companyId, start, count, filter, sortBy, sortOrder);
		if (resellerReports != null) {
			for (ResellerReport resellerReport : resellerReports) {
				resellerReportDtos.add(new ResellerReportDto(resellerReport));
			}
		}
		resellerReportDtoList = new ListResponse<ResellerReportDto>(getCount(filter, companyId), count, start,
				resellerReportDtos);
		return resellerReportDtoList;
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
	public void delete(long resellerReportId) throws DAOException {
		ResellerReport resellerReport = find(resellerReportId);
		delete(resellerReport);
	}

	@Override
	public ResellerReportDto update(long companyId, ResellerReportDto resellerReportDto, long resellerReportId) throws DAOException {
		ResellerReport resellerReport = find(resellerReportId);
		Reseller reseller = find(resellerReportDto.getResellerId(), Reseller.class);
		Company company = find(companyId, Company.class);
		resellerReport.setCompanyId(company.getId());
		this.dtoToEntityConvert(resellerReportDto, resellerReport);
		resellerReport.setReseller(reseller);
		resellerReportDto = new ResellerReportDto(resellerReport);
		return resellerReportDto;
	}

}
