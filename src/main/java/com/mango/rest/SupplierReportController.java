package com.mango.rest;

import static com.mango.utils.Constants.SERVICE_MANGO_SUPPLIERREPORTS;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mango.dto.ErrorResponse;
import com.mango.dto.ListResponse;
import com.mango.dto.SupplierReportDto;
import com.mango.exception.AuthenticationException;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.exception.ObjectNotFoundException;
import com.mango.manager.CompanyManager;
import com.mango.manager.SupplierReportManager;

@RestController
@RequestMapping(SERVICE_MANGO_SUPPLIERREPORTS)
@CrossOrigin
public class SupplierReportController extends Service {

	private static final Logger log = LoggerFactory.getLogger(SupplierReportController.class);

	@Autowired
	SupplierReportManager supplierReportManager;

	@Autowired
	private CompanyManager companyManager;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	ResponseEntity<?> create(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@Valid @RequestBody(required = true) SupplierReportDto supplierReportDto) {
		try {
			checkValidation(token);
			logRequest(supplierReportDto);
			supplierReportDto = supplierReportManager.create(companyId, supplierReportDto);
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
		return new ResponseEntity<SupplierReportDto>(supplierReportDto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{supplierReportId}", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	@ResponseBody
	ResponseEntity<?> update(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@PathVariable("supplierReportId") long supplierReportId,
			@Valid @RequestBody(required = true) SupplierReportDto supplierReportDto) {
		try {
			checkValidation(token);
			logRequest(supplierReportDto);
			supplierReportDto = supplierReportManager.update(companyId, supplierReportDto, supplierReportId);
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
		return new ResponseEntity<SupplierReportDto>(supplierReportDto, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public ResponseEntity<?> get(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@RequestParam(value = "start", required = false, defaultValue = "0") int start,
			@RequestParam(value = "count", required = false, defaultValue = "0") int count,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "filter", required = false) String filter) {
		logRequest("companyId:" + companyId);
		ListResponse<SupplierReportDto> supplierReportDto = null;
		try {
			checkValidation(token);
			supplierReportDto = supplierReportManager.getSupplierReports(companyId, start, count, sortBy, sortOrder,
					filter);
			logResponse(supplierReportDto);
		} catch (ObjectNotFoundException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (BadRequestException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.BAD_REQUEST + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (MangoException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ListResponse<SupplierReportDto>>(supplierReportDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/{supplierReportId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> delete(@CookieValue("token") String token,
			@PathVariable("supplierReportId") long supplierReportId) {
		logRequest("supplierReportId:" + supplierReportId);
		try {
			checkValidation(token);
			supplierReportManager.delete(supplierReportId);
			logResponse("Deleted Successfully");
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
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
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
