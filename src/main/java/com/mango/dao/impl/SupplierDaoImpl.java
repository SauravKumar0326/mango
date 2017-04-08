package com.mango.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mango.dao.DtoEntityConverter;
import com.mango.dao.SupplierDao;
import com.mango.dto.ListResponse;
import com.mango.dto.SupplierDto;
import com.mango.entities.Company;
import com.mango.entities.Supplier;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

@Repository
@Transactional
public class SupplierDaoImpl extends JPATemplate<Supplier>
		implements DtoEntityConverter<SupplierDto, Supplier>, SupplierDao {

	protected SupplierDaoImpl() {
		super(Supplier.class);
	}

	@Override
	public SupplierDto create(long companyId, SupplierDto supplierDto) throws DAOException {
		Supplier supplier = new Supplier();
		Company company = find(companyId, Company.class);
		this.dtoToEntityConvert(supplierDto, supplier);
		supplier.setCompany(company);
		save(supplier);
		supplierDto = new SupplierDto(supplier);
		return supplierDto;
	}

	@Override
	public SupplierDto update(long companyId, long supplierId, SupplierDto supplierDto) throws DAOException {
		Company company = find(companyId, Company.class);
		Supplier supplier = find(supplierId);
		supplier.setCompany(company);
		this.dtoToEntityConvert(supplierDto, supplier);
		supplierDto = new SupplierDto(supplier);
		return supplierDto;
	}

	@Override
	public void dtoToEntityConvert(SupplierDto dto, Supplier entity) throws DAOException {
		entity.setSupplierName(dto.getSupplierName());
		entity.setSupplierAddress(dto.getSupplierAddress());
		entity.setSupplierPhone(dto.getSupplierPhone());
		entity.setSupplierAdvanceAmount(dto.getSupplierAdvanceAmount());
		entity.setSupplierOpeningBalance(dto.getSupplierOpeningBalance());
	}

	@Override
	protected Predicate getTenantPredicate(long companyId, CriteriaBuilder cb, Root<Supplier> root) {
		Path<Supplier> companyPath = root.join("company", JoinType.LEFT);
		return cb.or(cb.equal(companyPath.get("id"), companyId), cb.isNull(root.get("company")));
	}

	@Override
	public ListResponse<SupplierDto> get(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws DAOException, BadRequestException {
		ListResponse<SupplierDto> supplierList = null;
		List<Supplier> suppliers = null;
		List<SupplierDto> supplierDtoList = new ArrayList<>();
		suppliers = findAll(companyId, start, count, filter, sortBy, sortOrder);
		if (suppliers != null) {
			for (Supplier supplier : suppliers) {
				supplierDtoList.add(new SupplierDto(supplier));
			}
		}
		supplierList = new ListResponse<SupplierDto>(getCount(filter, companyId), count, start, supplierDtoList);
		return supplierList;
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
	public SupplierDto get(long supplierId) throws DAOException {
		Supplier Supplier = find(supplierId);
		return new SupplierDto(Supplier);
	}

	@Override
	public void delete(long supplierId) throws DAOException {
		Supplier supplier = find(supplierId);
		delete(supplier);
	}

	@Override
	public List<SupplierDto> getPDF(long companyId) throws BadRequestException, DAOException {
		List<Supplier> suppliers = null;
		List<SupplierDto> supplierDtoList = new ArrayList<>();
		suppliers = findAll(companyId, 0, 0, null, null, null);
		if (suppliers != null) {
			for (Supplier supplier : suppliers) {
				supplierDtoList.add(new SupplierDto(supplier));
			}
		}
		return supplierDtoList;
	}
}
