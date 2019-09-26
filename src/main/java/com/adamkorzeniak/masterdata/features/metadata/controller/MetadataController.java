package com.adamkorzeniak.masterdata.features.metadata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.features.metadata.model.dto.MetadataResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Metadata;
import com.adamkorzeniak.masterdata.features.metadata.service.MetadataService;


@RestController
@RequestMapping("/v0")
public class MetadataController {
	
	private final MetadataService metadataService;
	
	@Autowired
	public MetadataController(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

	@GetMapping("/Metadata")
	public ResponseEntity<Metadata> retrieveMetadata() throws Exception {
		Metadata metadata = metadataService.buildMetadata();
		return new ResponseEntity<>(metadata, HttpStatus.OK);
	}

	@GetMapping("/Metadata2")
	public ResponseEntity<MetadataResponse> retrieveMetadataResponse() throws Exception {
		Metadata metadata = metadataService.buildMetadata();
		MetadataResponse response = metadataService.buildResponse(metadata);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
