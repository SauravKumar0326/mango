package com.mango.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.exception.MangoException;

public class JsonUtils {

	private static Logger log = Logger.getLogger(JsonUtils.class);
	public static DateFormat dateFormat = null;

	public static String toJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS a");
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(dateFormat);
		String json = mapper.writeValueAsString(object);
		return json;
	}

	public static <T> T fromJson(String jsonString, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, clazz);
	}

	public static <T> T fromJson(String jsonString, JavaType type)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, type);
	}

	public static <T> T fromJson(String jsonString, TypeReference<T> type)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonString, type);
	}

	public static Map<String, Object> getMapFromJson(String json) throws MangoException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JavaType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
			return mapper.readValue(json, type);
		} catch (IOException e) {
			log.error("JSON_PARSING_FAILED", e);
			throw new MangoException();
		}
	}

	public static Map<String, Object> getJsonMapFromObject(final Object object) throws MangoException {
		try {
			return getMapFromJson(toJson(object));
		} catch (IOException e) {
			log.error("JSON_PARSING_FAILED", e);
			throw new MangoException();
		}
	}
}
