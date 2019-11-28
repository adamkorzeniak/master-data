package com.adamkorzeniak.masterdata.logging;

import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;
import com.adamkorzeniak.masterdata.logging.model.RequestReceivedLog;
import com.adamkorzeniak.masterdata.logging.model.ResponseReturnedLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ControllerLoggingAspect {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Logger logger = Logger.getLogger(ControllerLoggingAspect.class.getName());

    @Autowired
    public ControllerLoggingAspect(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * After request is received in controller it creates request uuid and logs
     * details
     */
    @Before("PointcutDefinitions.controllers()")
    public void enteringController(JoinPoint joinPoint) {
        LoggingHelper.initializeContext(request);
        LogType logType = new RequestReceivedLog();
        Log log = LoggingHelper.generateLog(logType);
        logger.debug(log.toJsonMessage());
    }

    /**
     * Before sending response from controller it logs details and clears uuid
     */
    @AfterReturning("PointcutDefinitions.controllers()")
    public void successfullyExitingController(JoinPoint joinPoint) {
        int httpStatus = response.getStatus();
        LogType logType = new ResponseReturnedLog(httpStatus);
        Log log = LoggingHelper.generateLog(logType);
        LoggingHelper.clearContext();
        logger.debug(log.toJsonMessage());
    }
}
