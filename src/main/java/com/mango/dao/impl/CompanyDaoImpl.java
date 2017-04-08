package com.mango.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mango.dao.CompanyDao;
import com.mango.dao.DtoEntityConverter;
import com.mango.dto.CompanyDetail;
import com.mango.dto.CompanyDto;
import com.mango.entities.Company;
import com.mango.entities.Reseller;
import com.mango.entities.Supplier;
import com.mango.exception.DAOException;

@Repository
@Transactional
public class CompanyDaoImpl extends JPATemplate<Company>implements DtoEntityConverter<CompanyDto, Company>, CompanyDao {

	protected CompanyDaoImpl() {
		super(Company.class);
	}

	@Override
	public CompanyDto create(CompanyDto companyDto) throws DAOException {
		Company company = new Company();
		this.dtoToEntityConvert(companyDto, company);
		save(company);
		companyDto = new CompanyDto(company);
		return companyDto;
	}

	@Override
	public void dtoToEntityConvert(CompanyDto dto, Company entity) throws DAOException {
		entity.setCompanyName(dto.getCompanyName());
		entity.setCompanyAddress(dto.getCompanyAddress());
		entity.setCompanyPhone(dto.getCompanyPhone());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.setToken(UUID.randomUUID().toString());
	}

	@Override
	protected Predicate getTenantPredicate(long companyId, CriteriaBuilder cb, Root<Company> root) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void get(String companyId) throws DAOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", companyId);
		findOne(Company.GET_COMPANY_BY_TOKEN, params, Company.class);
	}

	@Override
	public void delete(long companyId) throws DAOException {
		Company company = find(companyId);
		delete(company);
	}

	@Override
	public boolean getCompanyCountByEmail(String email) throws DAOException {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("email", email);
			Long count = null;
			count = findOne(Company.COUNT_COMPANY_BY_EMAIL, params, Long.class);
			return count > 0;
		} catch (DAOException e) {
			throw e;
		}
	}

	@Override
	public CompanyDto getCompanyByMail(String email) throws DAOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		Company company = findOne(Company.GET_COMPANY_BY_EMAIL, params, Company.class);
		CompanyDto companyDto = new CompanyDto(company);
		companyDto.setPassword(company.getPassword());
		companyDto.setToken(company.getToken());
		companyDto.setId(company.getId());
		return companyDto;
	}

	@Override
	public CompanyDetail getCompanyDetail(long companyId) throws DAOException {
		Company company = find(companyId);
		CompanyDetail companyDetail = new CompanyDetail();
		long temp1 = 0;
		long temp2 = 0;
		if (CollectionUtils.isNotEmpty(company.getResellers())) {
			companyDetail.setResellerCount(company.getResellers().size());
			for (Reseller reseller : company.getResellers()) {
				if (CollectionUtils.isNotEmpty(reseller.getResellerReport())) {
					temp1 = temp1 + reseller.getResellerReport().size();
				}
			}
		}
		companyDetail.setResellerReportCount(temp1);
		if (CollectionUtils.isNotEmpty(company.getSupplier())) {
			companyDetail.setSupplierCount(company.getSupplier().size());
			for (Supplier supplier : company.getSupplier()) {
				if (CollectionUtils.isNotEmpty(supplier.getSupplierReport())) {
					temp2 = temp2 + supplier.getSupplierReport().size();
				}
			}
		}
		companyDetail.setSupplierReportCount(temp2);
		return companyDetail;
	}

}