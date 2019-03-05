package com.adamkorzeniak.masterdata.common.aop;

import java.util.StringJoiner;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.adamkorzeniak.masterdata.common.model.ExceptionResponse;

@Aspect
@Component
public class ErrorLoggingAspect {
	
	private Logger logger = Logger.getLogger(ErrorLoggingAspect.class.getName());
	

	@AfterReturning(pointcut="PointcutDefinitions.exceptionHandlers()", returning="result")
	public void successfullyExitingController(JoinPoint joinPoint, Object result) {
		String errorMessage = buildLogMessage(result);
		logger.debug("*****Error Response send*****\nCorrelationId=" + MDC.get("correlationId") + "\n" + errorMessage);
		MDC.clear();
	}
	
	private String buildLogMessage(Object response) {
		if (!(response instanceof ResponseEntity<?>)) {
			return "Unexpected Error: " + response;
		}
		
		ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) response;
		Object body = responseEntity.getBody();
		HttpStatus status = responseEntity.getStatusCode();
		
		return "HTTP Status: " + status + "\nResponse: " + body;
	}
	
	@AfterThrowing(pointcut="PointcutDefinitions.custom()", throwing="error")
	public void exitingMethodWithError(JoinPoint joinPoint, Throwable error) {
		String method = joinPoint.getSignature().toShortString();
		
		StringJoiner joiner = new StringJoiner("\n\t", "\n>>>>>Exception occurred<<<<<\n\t" , "");
		
		while (error != null) {
			joiner.add(error.toString());
			error = error.getCause();
		}
		
		String errorString = joiner.toString();
		
		logger.debug("*****Exiting method with error*****\nMethodName=" + method + "\nCorrelationId=" + MDC.get("correlationId") + errorString);
	}

}
