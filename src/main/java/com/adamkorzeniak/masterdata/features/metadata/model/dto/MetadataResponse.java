package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataResponse {

    private MetadataInfo info;
    private List<Module> modules;
}
