package com.adamkorzeniak.masterdata.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 
 * @author Representation of API Exception Response
 *
 */
@Getter
@ToString
@AllArgsConstructor
public class ExceptionResponse {

	private final String code;
	private final String title;
	private final String message;
}
