package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterSchema {

	private String type;
	private String example;
	private String format;
	@JsonProperty("enum")
	private List<String> enums;
}
