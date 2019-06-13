package com.adamkorzeniak.masterdata.logging;

import java.util.StringJoiner;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorLoggingAspect {

	private Logger logger = Logger.getLogger(ErrorLoggingAspect.class.getName());


	/**
	 * 
	 * After exception is handled in logs message and clears uuid
	 *  
	 */
	@AfterReturning(pointcut = "PointcutDefinitions.exceptionHandlers()", returning = "errorResult")
	public void successfullyExitingController(JoinPoint joinPoint, ResponseEntity<Object> errorResult) {
		String errorMessage = buildErrorReturnedMessage(errorResult);
		LoggingHelper.clearCorellationId();
		logger.debug(errorMessage);
	}
	
	/**
	 * 
	 * After exception in exception handler it logs the message
	 *  
	 */
	@AfterThrowing(pointcut = "PointcutDefinitions.custom()", throwing = "error")
	public void exitingMethodWithError(JoinPoint joinPoint, Throwable error) {
		String errorString = buildErrorUnhandledMessage(joinPoint, error);
		logger.debug(errorString);
	}

	private String buildErrorReturnedMessage(ResponseEntity<Object> responseEntity) {
		String errorMessagePrefix = "*****Error Response send*****\nCorrelationId=" + MDC.get("correlationId") + "\n";
		Object body = responseEntity.getBody();
		HttpStatus status = responseEntity.getStatusCode();
		return errorMessagePrefix + "HTTP Status: " + status + "\nResponse: " + body;
	}

	private String buildErrorUnhandledMessage(JoinPoint joinPoint, Throwable error) {
		String method = joinPoint.getSignature().toShortString();
		StringJoiner joiner = new StringJoiner("\n\t", "\n>>>>>Exception occurred<<<<<\n\t", "");
		while (error != null) {
			joiner.add(error.toString());
			error = error.getCause();
		}
		String errorString = joiner.toString();
		return "*****Exiting method with error*****\nMethodName=" + method + "\nCorrelationId="
		+ MDC.get("correlationId") + errorString;
	}
}
