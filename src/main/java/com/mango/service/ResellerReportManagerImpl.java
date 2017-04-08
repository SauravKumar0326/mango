package com.mango.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mango.dao.ResellerReportDao;
import com.mango.dto.ListResponse;
import com.mango.dto.ResellerReportDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.manager.ResellerReportManager;

@Component
public class ResellerReportManagerImpl implements ResellerReportManager {

	private static final Logger log = LoggerFactory.getLogger(ResellerReportManagerImpl.class);

	@Autowired
	ResellerReportDao resellerReportDao;

	@Override
	public ResellerReportDto create(long companyId, ResellerReportDto resellerReportDto) throws DAOException {
		try {
			resellerReportDto = resellerReportDao.create(companyId, resellerReportDto);
		} catch (DAOException e) {
			log.error("Failed to create Reseller Reports for Reseller {} with filter {}",
					resellerReportDto.getResellerId());
			throw e;
		}
		return resellerReportDto;
	}

	@Override
	public ListResponse<ResellerReportDto> getResellersReports(long companyId, int start, int count, String sortBy,
			String sortOrder, String filter) throws MangoException {
		ListResponse<ResellerReportDto> resellerReportDtoList = null;
		try {
			resellerReportDtoList = resellerReportDao.get(companyId, start, count, sortBy, sortOrder, filter);
		} catch (BadRequestException | DAOException e) {
			log.error("Failed to get Resellers Reports for company {} with filter {}", companyId, filter);
			throw e;
		}
		return resellerReportDtoList;
	}

	@Override
	public void delete(long resellerReportId) throws DAOException {
		try {
			resellerReportDao.delete(resellerReportId);
		} catch (DAOException e) {
			log.error("Failed to delete ResellerReport {}", resellerReportId);
			throw e;
		}
	}

	@Override
	public ResellerReportDto update(long companyId, ResellerReportDto resellerReportDto, long resellerReportId) throws DAOException {
		try {
			resellerReportDto = resellerReportDao.update(companyId, resellerReportDto, resellerReportId);
		} catch (DAOException e) {
			log.error("Failed to Update Reseller Reports for Reseller {} with filter {}",
					resellerReportDto.getResellerId());
			throw e;
		}
		return resellerReportDto;
	}
}
