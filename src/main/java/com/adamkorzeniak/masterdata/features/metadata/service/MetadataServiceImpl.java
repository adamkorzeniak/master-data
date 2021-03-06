package com.adamkorzeniak.masterdata.features.metadata.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adamkorzeniak.masterdata.exception.exceptions.MetadataResourceException;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.MetadataInfo;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.MetadataResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.Module;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.OperationResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Info;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Metadata;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Operation;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class MetadataServiceImpl implements MetadataService {

    @Value("${api.yaml.file}")
    private String FILE_PATH;

    @Override
    public Metadata buildMetadata() {
        Metadata metadata = readMetadataFromFile();
        validate(metadata);
        return metadata;
    }

    @Override
    public MetadataResponse buildResponse(Metadata metadata) {
        MetadataResponse response = new MetadataResponse();

        MetadataInfo metadataInfo = buildMetadataInfoResponse(metadata.getInfo());
        response.setInfo(metadataInfo);

        List<Module> modules = buildModules(metadata);
        response.setModules(modules);

        //TODO: Implement mapping

        return response;
    }

    private List<Module> buildModules(Metadata metadata) {
        List<Module> modules = new ArrayList<>();

        List<Tag> tags = metadata.getTags();

        for (Tag tag : tags) {
            Module module = new Module();
            module.setName(tag.getName());
            module.setDescription(tag.getDescription());
            modules.add(module);
        }

        Map<String, Map<String, Operation>> paths = metadata.getPaths();
        for (Map.Entry<String, Map<String, Operation>> entry : paths.entrySet()) {
            String resourceUrl = entry.getKey();
            Map<String, Operation> operations = entry.getValue();
            for (Map.Entry<String, Operation> operation : operations.entrySet()) {
                addOperationToModules(modules, operation);
            }
        }

        return modules;
    }

    private void addOperationToModules(List<Module> modules, Map.Entry<String, Operation> operation) {
        String method = operation.getKey();
        Operation operationValue = operation.getValue();
        List<String> tags = operationValue.getTags();

        OperationResponse operationResponse = new OperationResponse();
        operationResponse.setSummary(operationValue.getSummary());
        operationResponse.setOperationId(operationValue.getOperationId());
        operationResponse.setDescription(operationValue.getDescription());

//		modules.stream()
//			.filter(m -> tags.contains(m.getName()))
//			.forEach(m -> m.addResource(operationResponse));

    }

    private MetadataInfo buildMetadataInfoResponse(Info info) {
        MetadataInfo infoResponse = new MetadataInfo();

        infoResponse.setTitle(info.getTitle());
        infoResponse.setDescription(info.getDescription());
        infoResponse.setVersion(info.getVersion());
        infoResponse.setContactName(info.getContact().getName());
        infoResponse.setContactEmail(info.getContact().getEmail());
        infoResponse.setContactUrl(info.getContact().getUrl());

        return infoResponse;
    }

    private Metadata readMetadataFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = null;
        try {
            File file = ResourceUtils.getFile(FILE_PATH);
            is = new FileInputStream(file);
            return mapper.readValue(is, Metadata.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MetadataResourceException();
        }
    }

    private void validate(Metadata metadata) {
        //TODO: consider if needed
    }
}
