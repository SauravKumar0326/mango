package com.mango.rest;

import static com.mango.utils.Constants.SERVICE_MANGO_COMPANY;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mango.dto.CompanyDetail;
import com.mango.dto.CompanyDto;
import com.mango.dto.ErrorResponse;
import com.mango.dto.SignInDto;
import com.mango.exception.AuthenticationException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.exception.ObjectNotFoundException;
import com.mango.exception.ResourceAlreadyExistsException;
import com.mango.manager.CompanyManager;
import com.mango.validator.CompanyValidator;

@RestController
@RequestMapping(SERVICE_MANGO_COMPANY)
@CrossOrigin
public class CompanyController extends Service {
	private static final Logger log = LoggerFactory.getLogger(ResellerController.class);

	@Autowired
	private CompanyManager companyManager;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	ResponseEntity<?> create(@Valid @RequestBody(required = true) CompanyDto companyDto) {
		try {
			companyDto = companyManager.create(companyDto);
		} catch (ObjectNotFoundException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (ResourceAlreadyExistsException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.CONFLICT + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
		} catch (MangoException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CompanyDto>(companyDto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/sign_in", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseBody
	ResponseEntity<?> signin(@RequestBody(required = true) SignInDto signInDto) {
		CompanyDto companyDto = null;
		try {
			companyDto = companyManager.signin(signInDto);
		} catch (ObjectNotFoundException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (MangoException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CompanyDto>(companyDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/{companyToken}", method = RequestMethod.HEAD)
	@ResponseBody
	public ResponseEntity<?> getCompany(@PathVariable(value = "companyToken", required = true) String companyToken) {
		try {
			companyManager.get(companyToken);
		} catch (ObjectNotFoundException e) {
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (MangoException e) {
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{companyId}/details", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getCompanyDetails(@CookieValue("token") String token,
			@PathVariable("companyId") long companyId) {
		CompanyDetail companyDetail = null;
		try {
			checkValidation(token);
			companyDetail = companyManager.getCompanyDetail(companyId);
		} catch (ObjectNotFoundException e) {
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (MangoException e) {
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CompanyDetail>(companyDetail, HttpStatus.OK);
	}

	@RequestMapping(value = "/{companyId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> delete(@CookieValue("token") String token, @PathVariable("companyId") long companyId) {
		logRequest("companyId:", companyId);
		try {// TODO:Add more validation here..
			checkValidation(token);
			companyManager.delete(companyId);
			logResponse("Deleted Successfully");
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (ObjectNotFoundException e) {
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (MangoException e) {
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}

	@InitBinder("companyDto")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new CompanyValidator());
	}

	private void checkValidation(String token) throws AuthenticationException {
		if (StringUtils.isEmpty(token)) {
			throw new AuthenticationException("Failed to Authenticate");
		}
		try {
			companyManager.get(token);
		} catch (DAOException e) {
			throw new AuthenticationException();
		}
	}
}
