package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Metadata {

    private List<Module> modules;

    public Metadata(List<Module> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        String joinedModulesList = modules.stream()
                .map(Module::getName)
                .collect(Collectors.joining(", "));
        return String.format("Modules: %s", joinedModulesList);
    }
}
