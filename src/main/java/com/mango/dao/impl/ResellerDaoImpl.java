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
import com.mango.dao.ResellerDao;
import com.mango.dto.ListResponse;
import com.mango.dto.ResellerDto;
import com.mango.entities.Company;
import com.mango.entities.Reseller;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

@Repository
@Transactional
public class ResellerDaoImpl extends JPATemplate<Reseller>
		implements DtoEntityConverter<ResellerDto, Reseller>, ResellerDao {

	protected ResellerDaoImpl() {
		super(Reseller.class);
	}

	@Override
	public ResellerDto create(long companyId, ResellerDto resellerDto) throws DAOException {
		Reseller reseller = new Reseller();
		Company company = find(companyId, Company.class);
		this.dtoToEntityConvert(resellerDto, reseller);
		reseller.setCompany(company);
		save(reseller);
		resellerDto = new ResellerDto(reseller);
		return resellerDto;
	}

	@Override
	public ResellerDto update(long companyId, long resellerId, ResellerDto resellerDto) throws DAOException {
		Company company = find(companyId, Company.class);
		Reseller reseller = find(resellerId);
		reseller.setCompany(company);
		this.dtoToEntityConvert(resellerDto, reseller);
		return new ResellerDto(reseller);
	}

	@Override
	public void dtoToEntityConvert(ResellerDto dto, Reseller entity) throws DAOException {
		entity.setResellerName(dto.getResellerName());
		entity.setResellerAddress(dto.getResellerAddress());
		entity.setResellerClosingBalance(dto.getResellerClosingBalance());
		entity.setResellerOpeningBalance(dto.getResellerOpeningBalance());
		entity.setResellerPhone(dto.getResellerPhone());
	}

	@Override
	protected Predicate getTenantPredicate(long companyId, CriteriaBuilder cb, Root<Reseller> root) {
		Path<Reseller> companyPath = root.join("company", JoinType.LEFT);
		return cb.or(cb.equal(companyPath.get("id"), companyId), cb.isNull(root.get("company")));
	}

	@Override
	public ListResponse<ResellerDto> get(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws BadRequestException, DAOException {
		ListResponse<ResellerDto> resellerList = null;
		List<Reseller> resellers = null;
		List<ResellerDto> resellerDtoList = new ArrayList<>();
		resellers = findAll(companyId, start, count, filter, sortBy, sortOrder);
		if (resellers != null) {
			for (Reseller reseller : resellers) {
				resellerDtoList.add(new ResellerDto(reseller));
			}
		}
		resellerList = new ListResponse<ResellerDto>(getCount(filter, companyId), count, start, resellerDtoList);
		return resellerList;
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
	public ResellerDto get(long resellerId) throws DAOException {
		Reseller reseller = find(resellerId);
		return new ResellerDto(reseller);
	}

	@Override
	public void delete(long resellerId) throws DAOException {
		Reseller reseller = find(resellerId);
		delete(reseller);
	}

	@Override
	public List<ResellerDto> getPDF(long companyId) throws BadRequestException, DAOException {
		List<Reseller> resellers = null;
		List<ResellerDto> resellerDtoList = new ArrayList<>();
		resellers = findAll(companyId, 0, 0, null, null, null);
		if (resellers != null) {
			for (Reseller reseller : resellers) {
				resellerDtoList.add(new ResellerDto(reseller));
			}
		}
		return resellerDtoList;
	}
}
