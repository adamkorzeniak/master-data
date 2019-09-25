package com.adamkorzeniak.masterdata.logging;

import com.adamkorzeniak.masterdata.logging.model.Log;
import com.adamkorzeniak.masterdata.logging.model.LogType;

public interface LoggingService {

	void initializeContext();

	String retrieveCorrelationId();

	String retrieveRequestURI();

	String retrieveRequestMethod();

	int getHTTPStatus();

	Log generateLog(LogType logType);

	void clearContext();

}
