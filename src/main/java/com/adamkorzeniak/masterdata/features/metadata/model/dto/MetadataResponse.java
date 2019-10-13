package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MetadataResponse {

    private List<ModuleResponse> modules;

    public MetadataResponse(List<ModuleResponse> modules) {
        this.modules = modules;
    }

    @Override
    public String toString(){
        return "Modules: "  + modules.stream()
                .map(ModuleResponse::getName)
                .collect(Collectors.joining(", "));
    }
}
