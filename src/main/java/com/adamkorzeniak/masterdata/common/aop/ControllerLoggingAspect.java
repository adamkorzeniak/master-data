package com.adamkorzeniak.masterdata.common.aop;

import java.util.UUID;

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
		String uuid = UUID.randomUUID().toString();
		MDC.put("correlationId", uuid);
		
		String requestURL = request.getRequestURI();
		if (request.getQueryString() != null) {
			requestURL += "?" + request.getQueryString();
		}
		
		String message = String.format(
			"*****Request received*****\nCorrelationId=%s\n%s:%s",
				uuid,
				request.getMethod(),
				requestURL);
		
		logger.debug(message);
	}
	
	@AfterReturning("PointcutDefinitions.controllers()")
	public void successfullyExitingController(JoinPoint joinPoint) {
		logger.debug("*****Response send*****\nCorrelationId=" + MDC.get("correlationId"));
		MDC.clear();
	}

}
