package com.adamkorzeniak.masterdata.logging;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

	private final HttpServletRequest request;
	
	@Autowired
	public ControllerLoggingAspect(HttpServletRequest request) {
		this.request = request;
	}

	private Logger logger = Logger.getLogger(ControllerLoggingAspect.class.getName());

	/**
	 * 
	 * After request is received in controller it creates request uuid and logs
	 * details
	 * 
	 */
	@Before("PointcutDefinitions.controllers()")
	public void enteringController(JoinPoint joinPoint) {
		String uuid = LoggingHelper.generateCorrelationId();
		String requestURL = getRequestURL();
		String message = buildRequestReceivedMessage(uuid, requestURL);
		logger.debug(message);
	}

	/**
	 * 
	 * Before sending response from controller it logs details and clears uuid
	 * 
	 */
	@AfterReturning("PointcutDefinitions.controllers()")
	public void successfullyExitingController(JoinPoint joinPoint) {
		String uuid = LoggingHelper.generateCorrelationId();
		String message = buildResponseSendMessage(uuid);
		LoggingHelper.clearCorellationId();
		logger.debug(message);
	}

	private String getRequestURL() {
		String requestURL = request.getRequestURI();
		if (request.getQueryString() != null) {
			requestURL += "?" + request.getQueryString();
		}
		return requestURL;
	}

	private String buildRequestReceivedMessage(String uuid, String requestURL) {
		return String.format("*****Request received*****%nCorrelationId=%s%n%s:%s", uuid, request.getMethod(),
				requestURL);
	}

	private String buildResponseSendMessage(String uuid) {
		return "*****Response send*****\nCorrelationId=" + uuid;
	}
}
