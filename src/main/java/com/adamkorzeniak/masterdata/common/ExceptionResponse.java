package com.adamkorzeniak.masterdata.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

	private String code;
	private String title;
	private String message;
}
