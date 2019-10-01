package com.adamkorzeniak.masterdata.features.metadata.temp;

import com.adamkorzeniak.masterdata.features.metadata.controller.MetadataController;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Info;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Metadata;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v0")
public class MetadataTempController {

    private final MetadataController metadataController;

    @Autowired
    public MetadataTempController(MetadataController metadataController) {
        this.metadataController = metadataController;
    }

    @GetMapping("/Metadata/Info")
    public ResponseEntity<Info> retrieveMetadataInfo() {
        Metadata metadata = metadataController.retrieveMetadata().getBody();
        return new ResponseEntity<>(metadata.getInfo(), HttpStatus.OK);
    }

    @GetMapping("/Metadata/Tags")
    public ResponseEntity<List<Tag>> retrieveMetadataTags() {
        Metadata metadata = metadataController.retrieveMetadata().getBody();
        return new ResponseEntity<>(metadata.getTags(), HttpStatus.OK);
    }
}
