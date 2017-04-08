package com.mango.dao;

import com.mango.dto.CompanyDetail;
import com.mango.dto.CompanyDto;
import com.mango.exception.DAOException;

public interface CompanyDao {

	CompanyDto create(CompanyDto companyDto) throws DAOException;

	void get(String companyId) throws DAOException;

	void delete(long companyId) throws DAOException;

	boolean getCompanyCountByEmail(String email) throws DAOException;

	CompanyDto getCompanyByMail(String email) throws DAOException;

	CompanyDetail getCompanyDetail(long companyId) throws DAOException;

}
