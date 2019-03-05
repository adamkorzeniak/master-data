package com.adamkorzeniak.masterdata.common.logging;

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
		String enteringMessage = buildEnteringMethodMessage(joinPoint);
		logger.debug(enteringMessage);
	}
	
	@After("PointcutDefinitions.logic()")
	public void exitingMethod(JoinPoint joinPoint) {
		String exitingMessage = buildExitingMethodMessage(joinPoint);
		logger.debug(exitingMessage);
	}

	private String buildEnteringMethodMessage(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		return "*****Entering method*****\nMethodName=" + method + "\nCorrelationId=" + MDC.get("correlationId");
	}
	
	private String buildExitingMethodMessage(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		return "*****Exiting method*****\nMethodName=" + method + "\nCorrelationId=" + MDC.get("correlationId");
	}
	
}
