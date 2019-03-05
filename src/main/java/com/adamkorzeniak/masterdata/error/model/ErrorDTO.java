package com.adamkorzeniak.masterdata.error.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ErrorDTO {

	private Long id;
	@NotBlank
	private String title;
	@NotBlank
	private String details;

}
