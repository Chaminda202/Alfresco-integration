package com.dms.etf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	private JacksonUtil() {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

	private static ObjectMapper mapper = new ObjectMapper();

	public static String getToString(Object src) {
		try {
			return mapper.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			LOGGER.error("Object to String convert error");
		}
		return null;
	}

	public static byte[] convertObjectToJsonBytes(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		try {
			return mapper.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			LOGGER.error("Object to json bytes convert error");
		}
		return null;
	}
}
