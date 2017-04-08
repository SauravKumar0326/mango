package com.mango.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static com.mango.utils.Constants.SERVICE_MANGO_NOP;

@RestController
@RequestMapping(SERVICE_MANGO_NOP)
public class NopController {

	/**
	 * NOP API
	 * 
	 * @return 200 OK
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> get() {
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
