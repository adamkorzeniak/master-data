package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RequestBodyContent {

	private Map<String, ComponentReference> schema;
}
