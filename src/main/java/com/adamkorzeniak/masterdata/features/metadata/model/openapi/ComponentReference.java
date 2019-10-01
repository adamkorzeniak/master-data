package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentReference {

	@JsonProperty("$ref")
	private String ref;
}
