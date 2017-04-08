package com.mango.manager;

import com.mango.dto.ListResponse;
import com.mango.dto.SupplierDto;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;

public interface SupplierManager {

	SupplierDto create(long companyId, SupplierDto supplierDto) throws DAOException;

	ListResponse<SupplierDto> getSuppliers(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws MangoException;

	SupplierDto get(long supplierId) throws DAOException;

	void delete(long supplierId) throws DAOException;

	SupplierDto update(long companyId, long supplierId, SupplierDto supplierDto) throws DAOException;

	byte[] getPdf(long companyId) throws Exception;

}
