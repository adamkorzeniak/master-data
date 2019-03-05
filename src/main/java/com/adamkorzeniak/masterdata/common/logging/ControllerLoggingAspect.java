package com.adamkorzeniak.masterdata.common.logging;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

	@Autowired
	private HttpServletRequest request;

	private Logger logger = Logger.getLogger(ControllerLoggingAspect.class.getName());

	@Before("PointcutDefinitions.controllers()")
	public void enteringController(JoinPoint joinPoint) {
		String uuid = LoggingHelper.generateCorrelationId();
		String requestURL = getRequestURL();
		String message = buildRequestReceivedMessage(uuid, requestURL);
		logger.debug(message);
	}

	@AfterReturning("PointcutDefinitions.controllers()")
	public void successfullyExitingController(JoinPoint joinPoint) {
		String message = buildResponseSendMessage();
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
		return String.format(
				"*****Request received*****%nCorrelationId=%s%n%s:%s",
				uuid,
				request.getMethod(),
				requestURL);
	}

	private String buildResponseSendMessage() {
		return "*****Response send*****%nCorrelationId=" + MDC.get("correlationId");
	}
}
