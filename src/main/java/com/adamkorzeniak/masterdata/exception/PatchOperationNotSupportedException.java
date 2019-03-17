package com.adamkorzeniak.masterdata.exception;

public class PatchOperationNotSupportedException extends RuntimeException {

	public PatchOperationNotSupportedException(String operation, String resource) {
		super("Operation '" + operation + "' is not supported for Patch method on " + resource + " resource.");
	}
}
