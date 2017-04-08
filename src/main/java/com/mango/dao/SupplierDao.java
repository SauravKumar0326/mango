package com.mango.dao;

import java.util.List;

import com.mango.dto.ListResponse;
import com.mango.dto.SupplierDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

public interface SupplierDao {

	SupplierDto create(long companyId, SupplierDto supplierDto) throws DAOException;

	ListResponse<SupplierDto> get(long companyId, int start, int count, String sortBy, String sortOrder, String filter)
			throws DAOException, BadRequestException;

	SupplierDto get(long supplierId) throws DAOException;

	void delete(long supplierId) throws DAOException;

	SupplierDto update(long companyId, long supplierId, SupplierDto supplierDto) throws DAOException;

	List<SupplierDto> getPDF(long companyId) throws BadRequestException, DAOException;

}
