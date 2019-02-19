package com.adamkorzeniak.masterdata.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorLoggingAspect {
	
	private Logger logger = Logger.getLogger(ErrorLoggingAspect.class.getName());
	

	@AfterReturning("PointcutDefinitions.exceptionHandlers()")
	public void successfullyExitingController(JoinPoint joinPoint) {
		logger.debug("*****Error Response send*****\nCorrelationId=" + MDC.get("correlationId"));
		MDC.clear();
	}

}
