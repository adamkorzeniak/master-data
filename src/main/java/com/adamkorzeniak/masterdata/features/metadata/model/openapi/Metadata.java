package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"openapi", "security", "servers"})
public class Metadata {

	private Info info;
	private List<Tag> tags;
	private Map<String, Map<String, Operation>> paths;
	private MetadataComponent components;
}
