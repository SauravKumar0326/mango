package com.mango.dao;

import com.mango.dto.ListResponse;
import com.mango.dto.SupplierReportDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

public interface SupplierReportDao {

	SupplierReportDto create(long companyId, SupplierReportDto supplierReportDto) throws DAOException;

	ListResponse<SupplierReportDto> get(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws BadRequestException, DAOException;

	void delete(long supplierReportId) throws DAOException;

	SupplierReportDto update(long companyId, SupplierReportDto supplierReportDto, long supplierReportId) throws DAOException;

}
