package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyContent {

    private ComponentReference example;
	private ComponentReference schema;
}
