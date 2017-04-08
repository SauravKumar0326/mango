package com.mango.dao;

import java.util.List;

import com.mango.dto.ListResponse;
import com.mango.dto.ResellerDto;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;

public interface ResellerDao {

	ResellerDto create(long companyId, ResellerDto resellerDto) throws DAOException;

	ListResponse<ResellerDto> get(long companyId, int start, int count, String sortBy, String sortOrder, String filter) throws BadRequestException, DAOException;

	ResellerDto update(long companyId, long resellerId, ResellerDto resellerDto) throws DAOException;

	ResellerDto get(long resellerId) throws DAOException;

	void delete(long resellerId) throws DAOException;

	List<ResellerDto> getPDF(long companyId) throws BadRequestException, DAOException;

}
