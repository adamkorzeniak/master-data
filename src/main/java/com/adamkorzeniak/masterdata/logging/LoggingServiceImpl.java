package com.adamkorzeniak.masterdata.logging;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;

@Service
public class LoggingServiceImpl implements LoggingService {

    private final static String CORRELATION_ID_KEY = "correlationId";
    private final static String REQUEST_URI = "requestURI";
    private final static String REQUEST_METHOD = "requestMethod";

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Autowired
    public LoggingServiceImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Initializes Context
     */
    @Override
    public void initializeContext() {
        String uuid = UUID.randomUUID().toString();
        MDC.put(CORRELATION_ID_KEY, uuid);
        MDC.put(REQUEST_URI, getRequestURL());
        MDC.put(REQUEST_METHOD, request.getMethod());
    }

    private String getRequestURL() {
        String requestURL = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURL += "?" + request.getQueryString();
        }
        return requestURL;
    }

    @Override
    public String retrieveCorrelationId() {
        return (String) MDC.get(CORRELATION_ID_KEY);
    }

    @Override
    public String retrieveRequestURI() {
        return (String) MDC.get(REQUEST_URI);
    }

    @Override
    public String retrieveRequestMethod() {
        return (String) MDC.get(REQUEST_METHOD);
    }

    @Override
    public int getHTTPStatus() {
        return response.getStatus();
    }

    @Override
    public Log generateLog(LogType logType) {
        String correlationId = retrieveCorrelationId();
        String requestURI = retrieveRequestURI();
        String requestMethod = retrieveRequestMethod();
        String logTypeCode = logType.getType();
        return new Log(correlationId, requestURI, requestMethod, logTypeCode, logType);
    }

    /**
     * Clears context
     */
    @Override
    public void clearContext() {
        MDC.clear();
    }
}
