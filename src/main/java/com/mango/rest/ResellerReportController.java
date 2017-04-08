package com.mango.rest;

import static com.mango.utils.Constants.SERVICE_MANGO_RESELLERREPORTS;

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
import com.mango.dto.ResellerReportDto;
import com.mango.exception.AuthenticationException;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.exception.ObjectNotFoundException;
import com.mango.manager.CompanyManager;
import com.mango.manager.ResellerReportManager;

@RestController
@RequestMapping(SERVICE_MANGO_RESELLERREPORTS)
@CrossOrigin
public class ResellerReportController extends Service {

	private static final Logger log = LoggerFactory.getLogger(ResellerReportController.class);

	@Autowired
	private ResellerReportManager resellerReportManager;
	@Autowired
	private CompanyManager companyManager;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	ResponseEntity<?> create(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@Valid @RequestBody(required = true) ResellerReportDto resellerReportDto) {
		try {
			checkValidation(token);
			logRequest(resellerReportDto);
			resellerReportDto = resellerReportManager.create(companyId, resellerReportDto);
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
		return new ResponseEntity<ResellerReportDto>(resellerReportDto, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{resellerReportId}", method = RequestMethod.PUT, consumes = {
			"application/json" }, produces = { "application/json" })
	@ResponseBody
	ResponseEntity<?> update(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@PathVariable("resellerReportId") long resellerReportId,
			@Valid @RequestBody(required = true) ResellerReportDto resellerReportDto) {
		try {
			checkValidation(token);
			logRequest(resellerReportDto);
			resellerReportDto = resellerReportManager.update(companyId, resellerReportDto, resellerReportId);
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
		return new ResponseEntity<ResellerReportDto>(resellerReportDto, HttpStatus.OK);

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

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	@ResponseBody
	public ResponseEntity<?> get(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@RequestParam(value = "start", required = false, defaultValue = "0") int start,
			@RequestParam(value = "count", required = false, defaultValue = "0") int count,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			@RequestParam(value = "filter", required = false) String filter) {
		logRequest("companyId:" + companyId);
		ListResponse<ResellerReportDto> resellerReportDtoList = null;
		try {
			checkValidation(token);
			resellerReportDtoList = resellerReportManager.getResellersReports(companyId, start, count, sortBy,
					sortOrder, filter);
			logResponse(resellerReportDtoList);
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
		return new ResponseEntity<ListResponse<ResellerReportDto>>(resellerReportDtoList, HttpStatus.OK);
	}

	@RequestMapping(value = "/{resellerReportId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> delete(@CookieValue("token") String token,
			@PathVariable("resellerReportId") long resellerReportId) {
		logRequest("resellerReportId:" + resellerReportId);
		try {
			checkValidation(token);
			resellerReportManager.delete(resellerReportId);
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

	@Override
	protected Logger getLogger() {
		return log;
	}
}
