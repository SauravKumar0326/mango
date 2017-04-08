package com.mango.manager;

import com.mango.dto.CompanyDetail;
import com.mango.dto.CompanyDto;
import com.mango.dto.SignInDto;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;

public interface CompanyManager {

	CompanyDto create(CompanyDto companyDto) throws MangoException;

	void get(String companyId) throws DAOException;

	void delete(long companyId) throws DAOException;

	CompanyDto signin(SignInDto signInDto) throws MangoException;

	CompanyDetail getCompanyDetail(long companyId) throws DAOException;

}
