package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Module {

    private String name;
    private String description;
    private List<Operation2> operations;
}
