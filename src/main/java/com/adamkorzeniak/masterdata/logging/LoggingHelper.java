package com.adamkorzeniak.masterdata.logging;

import java.util.UUID;

import org.jboss.logging.MDC;

public class LoggingHelper {

	/**
	 * 
	 * Generates uuid
	 * 
	 */
	public static String generateCorrelationId() {
		String uuid = UUID.randomUUID().toString();
		MDC.put("correlationId", uuid);
		return uuid;
	}

	/**
	 * 
	 * Clears uuid
	 * 
	 */
	public static void clearCorellationId() {
		MDC.clear();
	}
}
