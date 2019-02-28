package com.adamkorzeniak.masterdata.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogicLoggingAspect {

	private Logger logger = Logger.getLogger(ControllerLoggingAspect.class.getName());

	@Before("PointcutDefinitions.logic()")
	public void enteringMethod(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		
		logger.debug("*****Entering method*****\nMethodName=" + method + "\nCorrelationId=" + MDC.get("correlationId"));
	}
	
	@After("PointcutDefinitions.logic()")
	public void exitingMethod(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		
		logger.debug("*****Exiting method*****\nMethodName=" + method + "\nCorrelationId=" + MDC.get("correlationId"));
	}
}
