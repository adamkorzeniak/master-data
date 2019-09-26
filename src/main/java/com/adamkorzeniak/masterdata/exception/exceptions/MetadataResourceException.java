package com.adamkorzeniak.masterdata.exception.exceptions;

/**
 * 
 * Exception thrown when duplicate user is being created
 *
 */
public class MetadataResourceException extends RuntimeException {

	private static final long serialVersionUID = -1611100019469836154L;

	public MetadataResourceException() {
		super("Metadata resource exception");
	}
}
