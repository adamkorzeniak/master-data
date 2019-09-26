package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentSchema {
	
	private String type;
	private String description;
	private List<String> required;
	private Map<String, ComponentSchemaProperty> properties;
}
