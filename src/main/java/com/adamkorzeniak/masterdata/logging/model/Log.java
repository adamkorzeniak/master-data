package com.adamkorzeniak.masterdata.logging.model;

import java.util.StringJoiner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Log {
	
	private final String correlationId;
	private final String requestURI;
	private final String requestMethod;
	private final String logTypeCode;
	private final LogType logType;
	
	public String toJsonMessage() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			jsonInString = buildFailedConvertionErrorLog();
			e.printStackTrace();
		}
		return jsonInString;
	}
	
	private String buildFailedConvertionErrorLog() {
		StringJoiner joiner = new StringJoiner(",\n", "{\n", "\n}");
		joiner.add(buildJsonField("correlationId", correlationId));
		joiner.add(buildJsonField("requestURI", requestURI));
		joiner.add(buildJsonField("requestMethod", requestMethod));
		joiner.add(buildJsonField("logTypeCode", "DESERIALIZATION_ERROR"));
		joiner.add(buildJsonField("logType", null));
		
		return joiner.toString();
	}
	
	private String buildJsonField(String fieldName, String fieldValue) {
		return String.format("\t\"%s\": \"%s\"", fieldName, fieldValue);
	}
}
