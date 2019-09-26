package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Info {

	private String title;
	private String description;
	private String version;
	private InfoContact contact;
}
