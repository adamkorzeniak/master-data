package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Module {

    private String name;
    private String description;
    private List<Operation> operations;

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
        this.operations = new ArrayList<>();
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    @Override
    public String toString() {
        return String.format("%s: %s, Operations: %s", name, description, operations);
    }
}
