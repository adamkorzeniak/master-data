package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"requestBody", "responses"})
public class Operation {
	
	private String summary;
	private String operationId;
	private String description;
	private List<String> tags;
	private List<Parameter> parameters;
}
