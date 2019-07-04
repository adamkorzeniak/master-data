package com.adamkorzeniak.masterdata.exception.exceptions;

/**
 * 
 * Exception thrown when duplicate user is being created
 *
 */
public class DuplicateUserException extends RuntimeException {

	private static final long serialVersionUID = -1611100019469836154L;

	public DuplicateUserException(String username) {
		super("User with username '" + username + "' already exists");
	}
}
