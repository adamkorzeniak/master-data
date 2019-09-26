package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter {
	
	private String in;
	private String name;
	private String description;
	private boolean required;
	private ParameterSchema schema;
}
