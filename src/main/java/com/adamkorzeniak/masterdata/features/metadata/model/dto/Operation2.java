package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Operation2 {

	private String method;
	private String path;
	private String operationId;
	private String summary;
	private String description;
	private RequestBody requestBody;
	
}
