package com.adamkorzeniak.masterdata.logging;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adamkorzeniak.masterdata.logging.model.ErrorOccurredLog;
import com.adamkorzeniak.masterdata.logging.model.ErrorResponseLog;
import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;

@Aspect
@Component
public class ErrorLoggingAspect {

	private Logger logger = Logger.getLogger(ErrorLoggingAspect.class.getName());
	private final LoggingService loggingService;
	
	@Autowired
	public ErrorLoggingAspect(LoggingServiceImpl loggingService) {
		this.loggingService = loggingService;
	}

	/**
	 * 
	 * After exception is handled in logs message and clears uuid
	 * 
	 */
	@AfterReturning(pointcut = "PointcutDefinitions.exceptionHandlers()")
	public void successfullyExitingController() {
		int httpStatus = loggingService.getHTTPStatus();
		LogType logType = new ErrorResponseLog(httpStatus);
		Log log = loggingService.generateLog(logType);
		loggingService.clearContext();
		logger.debug(log.toJsonMessage());
	}

	/**
	 * 
	 * After exception in exception handler it logs the message
	 * 
	 */
	@AfterThrowing(pointcut = "PointcutDefinitions.custom()", throwing = "error")
	public void exitingMethodWithError(JoinPoint joinPoint, Throwable error) {
		String methodName = joinPoint.getSignature().toShortString();
		List<Throwable> errors = new ArrayList<>();
		while (error != null) {
			errors.add(error);
			error = error.getCause();
		}
		LogType logType = new ErrorOccurredLog(methodName, errors);
		Log log = loggingService.generateLog(logType);
		logger.debug(log.toJsonMessage());
	}
}
