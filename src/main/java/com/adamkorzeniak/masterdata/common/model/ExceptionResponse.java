package com.adamkorzeniak.masterdata.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ExceptionResponse {

	private String code;
	private String title;
	private String message;
}
