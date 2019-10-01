package com.adamkorzeniak.masterdata.logging;

import java.util.UUID;

import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;
import org.jboss.logging.MDC;

import javax.servlet.http.HttpServletRequest;

public class LoggingHelper {

	private final static String CORRELATION_ID_KEY = "correlationId";
	private final static String REQUEST_URI = "requestURI";
	private final static String REQUEST_METHOD = "requestMethod";

	private LoggingHelper() {
	}

	private static String getRequestURL(HttpServletRequest request) {
		String requestURL = request.getRequestURI();
		if (request.getQueryString() != null) {
			requestURL += "?" + request.getQueryString();
		}
		return requestURL;
	}

	/**
	 * Clears uuid
	 */
	public static void clearContext() {
		MDC.clear();
	}

	public static void initializeContext(HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString();
		MDC.put(CORRELATION_ID_KEY, uuid);
		MDC.put(REQUEST_URI, getRequestURL(request));
		MDC.put(REQUEST_METHOD, request.getMethod());
	}

	public static Log generateLog(LogType logType) {
		String correlationId = retrieveCorrelationId();
		String requestURI = retrieveRequestURI();
		String requestMethod = retrieveRequestMethod();
		String logTypeCode = logType.getType();
		return new Log(correlationId, requestURI, requestMethod, logTypeCode, logType);
	}

	public static String retrieveCorrelationId() {
		return (String) MDC.get(CORRELATION_ID_KEY);
	}

	public static String retrieveRequestURI() {
		return (String) MDC.get(REQUEST_URI);
	}

	public static String retrieveRequestMethod() {
		return (String) MDC.get(REQUEST_METHOD);
	}
}
