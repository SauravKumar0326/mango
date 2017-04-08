package com.mango.manager;

import com.mango.dto.ListResponse;
import com.mango.dto.SupplierReportDto;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;

public interface SupplierReportManager {

	SupplierReportDto create(long companyId, SupplierReportDto supplierReportDto) throws DAOException;

	ListResponse<SupplierReportDto> getSupplierReports(long companyId, int start, int count, String sortBy,
			String sortOrder, String filter) throws MangoException;

	void delete(long supplierReportId) throws DAOException;

	SupplierReportDto update(long companyId, SupplierReportDto supplierReportDto, long supplierReportId) throws DAOException;

}
