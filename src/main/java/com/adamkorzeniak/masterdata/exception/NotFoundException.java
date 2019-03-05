package com.adamkorzeniak.masterdata.exception;

public class NotFoundException extends RuntimeException {

	public NotFoundException(String objectName, Long id) {
		super(objectName + " not found: id=" + id);
	}
}
