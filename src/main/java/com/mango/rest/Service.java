package com.mango.rest;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.mango.utils.LogUtility;

public class Service {

	private static final Logger logger = LoggerFactory.getLogger(Service.class);

	@Autowired
	protected HttpServletRequest servletRequest;

	protected void logRequest(Object... requestParameter) {
		if (servletRequest != null) {
			this.getLogger().info(LogUtility.logRequest(servletRequest.getRequestURL().toString(),
					servletRequest.getRemoteAddr(), servletRequest.getMethod(), requestParameter));
		}
	}

	protected void logRequest() {
		if (servletRequest != null) {
			this.getLogger().info(LogUtility.logRequest(servletRequest.getRequestURL().toString(),
					servletRequest.getRemoteAddr(), servletRequest.getMethod()));
		}
	}

	protected void logResponse(Object responseObject) {
		if (servletRequest != null) {
			this.getLogger().info(LogUtility.logResponse(servletRequest.getRequestURL().toString(),
					servletRequest.getMethod(), responseObject));
		}
	}

	protected Logger getLogger() {
		return logger;
	}

	protected Locale getRequestedLocale() {
		return LocaleUtils.isAvailableLocale(servletRequest.getLocale()) ? servletRequest.getLocale()
				: tryToParseLocaleFromHeader();
	}

	private Locale tryToParseLocaleFromHeader() {
		String lang = servletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
		logger.info("Trying to parse Locale from String {}", lang);
		Locale locale = Locale.ENGLISH;
		try {
			lang = lang.split(",")[0];
			locale = LocaleUtils.toLocale(lang);
			logger.info("Parsed locale : {}", locale);
		} catch (Exception e) {
			logger.warn("Couldn't parse Accept-Language {}, exception {}", lang, e.getMessage());
		}
		return locale;
	}
}
