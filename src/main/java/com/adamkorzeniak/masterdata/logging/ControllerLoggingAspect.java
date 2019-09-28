package com.adamkorzeniak.masterdata.logging;

import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;
import com.adamkorzeniak.masterdata.logging.model.RequestReceivedLog;
import com.adamkorzeniak.masterdata.logging.model.ResponseReturnedLog;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

    private final Logger logger = Logger.getLogger(ControllerLoggingAspect.class.getName());
    private final LoggingService loggingService;

    @Autowired
    public ControllerLoggingAspect(LoggingServiceImpl loggingService) {
        this.loggingService = loggingService;
    }


    /**
     * After request is received in controller it creates request uuid and logs
     * details.
     */
    @Before("PointcutDefinitions.controllers()")
    public void enteringController() {
        loggingService.initializeContext();
        LogType logType = new RequestReceivedLog();
        Log log = loggingService.generateLog(logType);
        logger.debug(log.toJsonMessage());
    }

    /**
     * Before sending response from controller it logs details and clears uuid
     */
    @AfterReturning("PointcutDefinitions.controllers()")
    public void successfullyExitingController() {
        int httpStatus = loggingService.getHTTPStatus();
        LogType logType = new ResponseReturnedLog(httpStatus);
        Log log = loggingService.generateLog(logType);
        loggingService.clearContext();
        logger.debug(log.toJsonMessage());
    }
}
