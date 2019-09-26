package com.adamkorzeniak.masterdata.features.metadata.service;

import com.adamkorzeniak.masterdata.features.metadata.model.dto.MetadataResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Metadata;

public interface MetadataService {
	
	Metadata buildMetadata();
	
	MetadataResponse buildResponse(Metadata metadata);

}
