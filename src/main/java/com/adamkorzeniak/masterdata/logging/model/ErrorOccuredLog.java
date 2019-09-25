package com.adamkorzeniak.masterdata.logging.model;

import java.util.List;

import lombok.Getter;

@Getter
public class ErrorOccuredLog implements LogType {
	
	private static final String TYPE_MESSAGE = "ERROR_OCCURED";
	
	private final String methodName;
	private final List<Throwable> errors;
	private final String message;
	
	public ErrorOccuredLog(String methodName, List<Throwable> errors) {
		this.methodName = methodName;
		this.errors = errors;
		this.message = "Error occured";
	}

	@Override
	public String getType() {
		return TYPE_MESSAGE;
	}
}
