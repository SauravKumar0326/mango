package com.mango.manager;

import com.mango.dto.ListResponse;
import com.mango.dto.ResellerReportDto;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;

public interface ResellerReportManager {

	public ResellerReportDto create(long companyId, ResellerReportDto resellerReportDto) throws DAOException;

	public ListResponse<ResellerReportDto> getResellersReports(long companyId, int start, int count, String sortBy,
			String sortOrder, String filter) throws MangoException;

	public void delete(long resellerReportId) throws DAOException;

	public ResellerReportDto update(long companyId, ResellerReportDto resellerReportDto, long resellerReportId) throws DAOException;

}
