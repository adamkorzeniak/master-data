package com.adamkorzeniak.masterdata.logging;

import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;
import org.jboss.logging.MDC;

import java.util.UUID;

public class LoggingHelper {

    private static final String CORRELATION_ID_KEY = "correlationId";
    private static final String REQUEST_URI = "requestURI";
    private static final String REQUEST_METHOD = "requestMethod";

    private LoggingHelper() {
    }

    private static String getRequestURL() {
        return REQUEST_URI;
    }

    /**
     * Clears uuid
     */
    public static void clearContext() {
        MDC.clear();
    }

    public static void initializeContext() {
        String uuid = UUID.randomUUID().toString();
        MDC.put(CORRELATION_ID_KEY, uuid);
        MDC.put(REQUEST_URI, "XXX");
        MDC.put(REQUEST_METHOD, "GET");
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
