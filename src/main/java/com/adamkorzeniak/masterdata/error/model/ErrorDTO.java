package com.adamkorzeniak.masterdata.error.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

	private Long id;

	@NotBlank
	private String errorId;

	@NotBlank
	private String appId;

	private String name;

	private String details;

	private String status;

	private String url;

	private String stack;

	private Long time;
}
