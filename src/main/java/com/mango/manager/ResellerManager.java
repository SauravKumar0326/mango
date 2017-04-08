package com.mango.manager;

import com.mango.dto.ListResponse;
import com.mango.dto.ResellerDto;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;

public interface ResellerManager {

	ResellerDto create(long companyId, ResellerDto resellerDto) throws DAOException;

	ListResponse<ResellerDto> getResellers(long companyId, int start, int count, String sortBy, String sortOrder,
			String filter) throws MangoException;

	ResellerDto update(long companyId, long resellerId, ResellerDto resellerDto) throws DAOException;

	ResellerDto get(long resellerId) throws DAOException;

	void delete(long resellerId) throws DAOException;

	byte[] getPdf(long companyId) throws MangoException, Exception;

}
