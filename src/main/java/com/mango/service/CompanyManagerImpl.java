package com.mango.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mango.dao.CompanyDao;
import com.mango.dto.CompanyDetail;
import com.mango.dto.CompanyDto;
import com.mango.dto.SignInDto;
import com.mango.exception.AuthenticationException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.exception.ResourceAlreadyExistsException;
import com.mango.manager.CompanyManager;

@Component
public class CompanyManagerImpl implements CompanyManager {

	private static final Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

	private static String[] emailList = { "kishor@gmail.com", "kishor1@gmail.com" };
	@Autowired
	private CompanyDao companyDao;

	@Override
	public CompanyDto create(CompanyDto companyDto) throws MangoException {
		try {
			//checkEmailList(companyDto.getEmail());
			checkIfCompanyAlreadyExists(companyDto);
			companyDto = companyDao.create(companyDto);
		} catch (DAOException e) {
			log.error("Failed to create company {}", companyDto.getCompanyName());
			throw e;
		}
		return companyDto;
	}

	private void checkEmailList(String email) throws AuthenticationException {
		if (!Arrays.asList(emailList).contains(email)) {
			throw new AuthenticationException();
		}
	}

	private void checkIfCompanyAlreadyExists(CompanyDto companyDto)
			throws DAOException, ResourceAlreadyExistsException {
		if (companyDao.getCompanyCountByEmail(companyDto.getEmail())) {
			log.error("A Company with email {} already exist.", companyDto.getEmail());
			throw new ResourceAlreadyExistsException("A Company with email " + companyDto.getEmail() + " already exist.");
		}

	}

	@Override
	public void get(String companyId) throws DAOException {
		try {
			companyDao.get(companyId);
		} catch (DAOException e) {
			log.error("Failed to get company {}", companyId);
			throw e;
		}
	}

	@Override
	public void delete(long companyId) throws DAOException {
		try {
			companyDao.delete(companyId);
		} catch (DAOException e) {
			log.error("Failed to delete company {}", companyId);
			throw e;
		}
	}

	@Override
	public CompanyDto signin(final SignInDto signInDto) throws AuthenticationException, DAOException {
		CompanyDto company = null;
		try {
			CompanyDto company1 = companyDao.getCompanyByMail(signInDto.getEmail());
			if (!(signInDto.getEmail().equals(company1.getEmail())
					&& signInDto.getPassword().equals(company1.getPassword()))) {
				throw new AuthenticationException();
			}
			company = new CompanyDto();
			company.setId(company1.getId());
			company.setToken(company1.getToken());
			company.setEmail(company1.getEmail());
			company.setCompanyName(company1.getCompanyName());
		} catch (DAOException e) {
			log.error("Failed to sign_in company {}", signInDto.getEmail());
			throw e;
		}
		return company;
	}

	@Override
	public CompanyDetail getCompanyDetail(long companyId) throws DAOException {
		CompanyDetail companyDetail = null;
		try {
			companyDetail = companyDao.getCompanyDetail(companyId);
		} catch (DAOException e) {
			log.error("Failed to get company Detail {}", companyId);
			throw e;
		}
		return companyDetail;
	}

}
