package com.adamkorzeniak.masterdata.logging;

import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;
import com.adamkorzeniak.masterdata.logging.model.MethodEnteredLog;
import com.adamkorzeniak.masterdata.logging.model.MethodExitedLog;
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

	private Logger logger = Logger.getLogger(LogicLoggingAspect.class.getName());

	/**
	 * Logs message when entering every service, helper and repository method
	 */
	@Before("PointcutDefinitions.logic()")
	public void enteringMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		LogType logType = new MethodEnteredLog(methodName);
		Log log = LoggingHelper.generateLog(logType);
		logger.debug(log.toJsonMessage());
	}

	/**
	 * Logs message after exiting from every service, helper and repository method
	 */
	@After("PointcutDefinitions.logic()")
	public void exitingMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		LogType logType = new MethodExitedLog(methodName);
		Log log = LoggingHelper.generateLog(logType);
		logger.debug(log.toJsonMessage());
	}

}
