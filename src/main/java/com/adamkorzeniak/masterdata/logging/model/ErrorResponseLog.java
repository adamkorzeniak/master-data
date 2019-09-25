package com.adamkorzeniak.masterdata.logging.model;

import lombok.Getter;

@Getter
public class ErrorResponseLog implements LogType {
	
	private static final String TYPE_MESSAGE = "ERROR_RESPONSE";

	private final int httpStatus;
	private final String message;
	
	public ErrorResponseLog(int httpStatus) {
		this.httpStatus = httpStatus;
		this.message = "Error response returned";
	}

	@Override
	public String getType() {
		return TYPE_MESSAGE;
	}
}
