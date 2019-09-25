package com.adamkorzeniak.masterdata.logging.model;

import lombok.Getter;

@Getter
public class ResponseReturnedLog implements LogType {
	
	private static final String TYPE_MESSAGE = "RESPONSE_RETURNED";
	
	private final String message;
	private final int httpStatus;
	
	public ResponseReturnedLog(int httpStatus) {
		this.httpStatus = httpStatus;
		this.message = "Response returned";
	}
	
	@Override
	public String getType() {
		return TYPE_MESSAGE;
	}
}
