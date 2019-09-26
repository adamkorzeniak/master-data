package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataInfo {

	private String title;
	private String description;
	private String version;
	private String contactName;
	private String contactEmail;
	private String contactUrl;
}
