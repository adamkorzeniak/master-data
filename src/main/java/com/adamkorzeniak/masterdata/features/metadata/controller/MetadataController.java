package com.adamkorzeniak.masterdata.features.metadata.controller;

import com.adamkorzeniak.masterdata.features.metadata.model.dto.Metadata;
import com.adamkorzeniak.masterdata.features.metadata.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v0")
public class MetadataController {

    private final MetadataService metadataService;

    @Autowired
    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping("/Metadata")
    public ResponseEntity<Metadata> retrieveMetadataResponse() {
        Metadata response = metadataService.buildMetadata();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
