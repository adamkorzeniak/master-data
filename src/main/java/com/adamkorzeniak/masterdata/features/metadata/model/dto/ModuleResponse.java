package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ModuleResponse {

    private String name;
    private String description;
    private List<OperationResponse> operations;

    public ModuleResponse(String name, String description) {
        this.name = name;
        this.description = description;
        this.operations = new ArrayList<>();
    }

    public void addOperation(OperationResponse operation) {
        operations.add(operation);
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
