package com.mango.rest;

import static com.mango.utils.Constants.SERVICE_MANGO_RESELLER;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.mango.dto.ResellerDto;
import com.mango.exception.AuthenticationException;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.exception.ObjectNotFoundException;
import com.mango.manager.CompanyManager;
import com.mango.manager.ResellerManager;

@RestController
@RequestMapping(SERVICE_MANGO_RESELLER)
@CrossOrigin
public class ResellerController extends Service {

	private static final Logger log = LoggerFactory.getLogger(ResellerController.class);

	@Autowired
	private ResellerManager resellerManager;
	@Autowired
	private CompanyManager companyManager;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	@ResponseBody
	ResponseEntity<?> create(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@RequestBody ResellerDto resellerDto) {
		try {
			checkValidation(token);
			logRequest(resellerDto);
			resellerDto = resellerManager.create(companyId, resellerDto);
			logResponse(resellerDto);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (ObjectNotFoundException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (MangoException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResellerDto>(resellerDto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{resellerId}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseBody
	ResponseEntity<?> update(@CookieValue("token") String token, @PathVariable("companyId") long companyId,
			@PathVariable("resellerId") long resellerId, @RequestBody ResellerDto resellerDto) {
		try {
			checkValidation(token);
			logRequest(resellerDto);
			resellerDto = resellerManager.update(companyId, resellerId, resellerDto);
			logResponse(resellerDto);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (ObjectNotFoundException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (MangoException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResellerDto>(resellerDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/{resellerId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getCompany(@CookieValue("token") String token,
			@PathVariable("resellerId") long resellerId) {
		logRequest("resellerId:" + resellerId);
		ResellerDto resellerDto = null;
		try {
			checkValidation(token);
			resellerDto = resellerManager.get(resellerId);
			logResponse(resellerDto);
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
		return new ResponseEntity<ResellerDto>(resellerDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/{resellerId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> delete(@CookieValue("token") String token, @PathVariable("resellerId") long resellerId) {
		logRequest("resellerId:" + resellerId);
		try {
			checkValidation(token);
			resellerManager.delete(resellerId);
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

	@RequestMapping(value = "/getpdf", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getPdf(@CookieValue("token") String token, @PathVariable("companyId") long companyId) {
		logRequest("companyId:" + companyId);
		byte[] contents = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			checkValidation(token);
			contents = resellerManager.getPdf(companyId);
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "Reseller.pdf";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
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
		} catch (Exception e) {
			logResponse("Status:" + HttpStatus.INTERNAL_SERVER_ERROR + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
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
		ListResponse<ResellerDto> resellerList = null;
		try {
			checkValidation(token);
			resellerList = resellerManager.getResellers(companyId, start, count, sortBy, sortOrder, filter);
			logResponse(resellerList);
		} catch (AuthenticationException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.UNAUTHORIZED + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (ObjectNotFoundException e) {
			log.error(e.getMessage(), e);
			logResponse("Status:" + HttpStatus.NOT_FOUND + " Response:" + e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
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
		return new ResponseEntity<ListResponse<ResellerDto>>(resellerList, HttpStatus.OK);
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
