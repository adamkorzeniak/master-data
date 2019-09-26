package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentSchemaProperty {

	private String description;
	private String type;
	private String example;
	@JsonProperty("enum")
	private List<String> enums;
	private boolean readOnly;
	private boolean writeOnly;
}
