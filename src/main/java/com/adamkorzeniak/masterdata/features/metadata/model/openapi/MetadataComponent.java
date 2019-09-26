package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"securitySchemes"})
public class MetadataComponent {
	
	private Map<String, ComponentSchema> schemas;
	private Map<String, Example> examples;
	private Map<String, Response> responses;
}
