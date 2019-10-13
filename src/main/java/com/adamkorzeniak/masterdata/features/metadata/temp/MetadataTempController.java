package com.adamkorzeniak.masterdata.features.metadata.temp;

import com.adamkorzeniak.masterdata.features.metadata.controller.MetadataController;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.MetadataResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.ModelSchemaResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.ModuleResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v0")
public class MetadataTempController {

    private final MetadataController metadataController;

    @Autowired
    public MetadataTempController(MetadataController metadataController) {
        this.metadataController = metadataController;
    }

    private List<ModuleResponse> retrieveModules() {
        ResponseEntity<MetadataResponse> metadata = metadataController.retrieveMetadataResponse();
        return metadata.getBody().getModules();
    }

    private List<OperationResponse> retrieveOperations(String moduleName) {
        List<OperationResponse> result = new ArrayList<>();
        for (ModuleResponse module : retrieveModules()) {
            if (moduleName == null || moduleName.equals(module.getName())) {
                result.addAll(module.getOperations());
            }
        }
        return result;
    }

    @GetMapping("/Metadata/Modules/Operations/")
    public List<OperationResponse> retrieveMetadataOperations() {
        List<OperationResponse> operations = modify(retrieveOperations(null));
        return operations.stream().map(
                operation -> {
                    OperationResponse operationResult = new OperationResponse();
                    operationResult.setMethod(operation.getMethod());
                    operationResult.setUrl(operation.getUrl());
                    operationResult.setOperationId(operation.getOperationId());
                    operationResult.setSummary(operation.getSummary());
                    operationResult.setDescription(operation.getDescription());
                    return operationResult;
                }
        ).collect(Collectors.toList());
    }

    @GetMapping("/Metadata/Modules/Operations/Parameters")
    public Map<String, List<ModelSchemaResponse>> retrieveMetadataOperationsParameters() {
        List<OperationResponse> operations = modify(retrieveOperations(null));
        Map<String, List<ModelSchemaResponse>> result = new HashMap<>();

        return result;
    }

    private List<OperationResponse> modify(List<OperationResponse> operations) {
        for (OperationResponse operation : operations) {
                operation.setUriParams(null);
                operation.setQueryParams(null);
                operation.setRequestBody(null);
                operation.setResponses(null);
        }
        return operations;
    }


}
