package com.adamkorzeniak.masterdata.logging;

import com.adamkorzeniak.masterdata.logging.model.ErrorOccurredLog;
import com.adamkorzeniak.masterdata.logging.model.ErrorResponseLog;
import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ErrorLoggingAspect {

    private Logger logger = Logger.getLogger(ErrorLoggingAspect.class.getName());

    /**
     * After exception is handled in logs message and clears uuid
     */
    @AfterReturning(pointcut = "PointcutDefinitions.exceptionHandlers()", returning = "errorResult")
    public void successfullyExitingController(JoinPoint joinPoint, ResponseEntity<Object> errorResult) {
        int httpStatus = 200;
        LogType logType = new ErrorResponseLog(httpStatus);
        Log log = LoggingHelper.generateLog(logType);
        LoggingHelper.clearContext();
        logger.debug(log.toJsonMessage());
    }

    /**
     * After exception in exception handler it logs the message
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
        Log log = LoggingHelper.generateLog(logType);
        logger.debug(log.toJsonMessage());
    }
}
