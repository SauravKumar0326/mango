package com.mango.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mango.dao.SupplierReportDao;
import com.mango.dto.ListResponse;
import com.mango.dto.SupplierReportDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.manager.SupplierReportManager;

@Component
public class SupplierReportManagerImpl implements SupplierReportManager {

	private static final Logger log = LoggerFactory.getLogger(SupplierReportManagerImpl.class);

	@Autowired
	SupplierReportDao supplierReportDao;

	@Override
	public SupplierReportDto create(long companyId, SupplierReportDto supplierReportDto) throws DAOException {
		try {
			supplierReportDto = supplierReportDao.create(companyId, supplierReportDto);
		} catch (DAOException e) {
			log.error("Failed to create Supplier Reports for Reseller {} with filter {}",
					supplierReportDto.getSupplierId());
			throw e;
		}
		return supplierReportDto;
	}

	@Override
	public ListResponse<SupplierReportDto> getSupplierReports(long companyId, int start, int count, String sortBy,
			String sortOrder, String filter) throws MangoException {
		ListResponse<SupplierReportDto> supplierReportDtoList = null;
		try {
			supplierReportDtoList = supplierReportDao.get(companyId, start, count, sortBy, sortOrder, filter);
		} catch (BadRequestException | DAOException e) {
			log.error("Failed to get Supplier Reports for company {} with filter {}", companyId, filter);
			throw e;
		}
		return supplierReportDtoList;
	}

	@Override
	public void delete(long supplierReportId) throws DAOException {
		try {
			supplierReportDao.delete(supplierReportId);
		} catch (DAOException e) {
			log.error("Failed to delete Supplier Report {}", supplierReportId);
			throw e;
		}
	}

	@Override
	public SupplierReportDto update(long companyId, SupplierReportDto supplierReportDto, long supplierReportId)
			throws DAOException {
		try {
			supplierReportDto = supplierReportDao.update(companyId, supplierReportDto, supplierReportId);
		} catch (DAOException e) {
			log.error("Failed to Update Supplier Reports for Reseller {} with filter {}",
					supplierReportDto.getSupplierId());
			throw e;
		}
		return supplierReportDto;
	}
}
