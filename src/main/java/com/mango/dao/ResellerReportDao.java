package com.mango.dao;

import com.mango.dto.ListResponse;
import com.mango.dto.ResellerReportDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

public interface ResellerReportDao {

	ResellerReportDto create(long companyId,ResellerReportDto resellerReportDto)throws DAOException;

	ListResponse<ResellerReportDto> get(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws BadRequestException, DAOException;

	void delete(long resellerReportId) throws DAOException;

	ResellerReportDto update(long companyId, ResellerReportDto resellerReportDto, long resellerReportId) throws DAOException;

}
