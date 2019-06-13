package com.adamkorzeniak.masterdata.exception.exceptions;

/**
 * 
 * Exception is thrown when operation specified in body of PATCH request is not supported for given resource.
 *
 */
public class PatchOperationNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = 4026587234445897874L;

	public PatchOperationNotSupportedException(String operation, String resource) {
		super("Operation '" + operation + "' is not supported for Patch method on " + resource + " resource.");
	}
}
