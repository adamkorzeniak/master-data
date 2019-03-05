package com.adamkorzeniak.masterdata.common.logging;

import java.util.UUID;

import org.jboss.logging.MDC;

public class LoggingHelper {
	
	private LoggingHelper() {}

	public static String generateCorrelationId() {
		String uuid = UUID.randomUUID().toString();
		MDC.put("correlationId", uuid);
		return uuid;
	}

	public static void clearCorellationId() {
		MDC.clear();
	}
}
