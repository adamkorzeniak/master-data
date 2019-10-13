package com.adamkorzeniak.masterdata.features.metadata.service;

import com.adamkorzeniak.masterdata.exception.exceptions.MetadataParamTypeNotSupported;
import com.adamkorzeniak.masterdata.exception.exceptions.MetadataResourceException;
import com.adamkorzeniak.masterdata.exception.exceptions.ModuleNotFoundException;
import com.adamkorzeniak.masterdata.exception.exceptions.OpenapiComponentNotSupportedException;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.*;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MetadataServiceImpl implements MetadataService {

    @Value("${api.yaml.file}")
    private String FILE_PATH;

    private static final String METADATA_CONTENT_TYPE = "application/json";

    private static final String SCHEMA_COMPONENT_REFERENCE_TYPE = "schemas";
    private static final String REQUEST_BODY_COMPONENT_REFERENCE_TYPE = "requestBodies";
    private static final String RESPONSE_COMPONENT_REFERENCE_TYPE = "responses";

    private static final String URI_PARAM_TYPE_NAME = "path";
    private static final String QUERY_PARAM_TYPE_NAME = "query";
    private static final String HEADER_TYPE_NAME = "header";

    private Metadata metadata;

    @Override
    public MetadataResponse buildMetadataResponse() {
        metadata = readMetadataFromFile();
        List<ModuleResponse> modules = buildModulesResponse();
        List<OperationResponse> operations = buildOperationResponse();
        assignOperationsToModules(modules, operations);
        return new MetadataResponse(modules);
    }

    private Metadata readMetadataFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is;
        try {
            File file = ResourceUtils.getFile(FILE_PATH);
            is = new FileInputStream(file);
            return mapper.readValue(is, Metadata.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MetadataResourceException();
        }
    }

    private List<OperationResponse> buildOperationResponse() {
        Map<String, Map<String, Path>> paths = metadata.getPaths();
        List<OperationResponse> operationResponses = new ArrayList<>();

        for (Map.Entry<String, Map<String, Path>> pathEntry : paths.entrySet()) {
            String url = pathEntry.getKey();

            for (Map.Entry<String, Path> operationEntry : pathEntry.getValue().entrySet()) {
                Path operation = operationEntry.getValue();

                OperationResponse operationResponse = buildOperationResponse(
                        url,
                        operationEntry.getKey(),
                        operation);

                assignParamsToOperationResponse(operation, operationResponse);
                assignRequestBodyToOperationResponse(operation, operationResponse);
                assignResponsesToOperationResponse(operation, operationResponse);

                operationResponses.add(operationResponse);
            }
        }
        return operationResponses;
    }

    private void assignResponsesToOperationResponse(Path operation, OperationResponse operationResponse) {
        Map<String, ComponentReference> responses = operation.getResponses();
        if (responses != null && !responses.isEmpty()) {
            List<ModelSchemaResponse> responsesResult = responses.entrySet().stream()
                    .map(this::buildResponseModel)
                    .collect(Collectors.toList());
            operationResponse.setResponses(responsesResult);
        }
    }

    private void assignRequestBodyToOperationResponse(Path operation, OperationResponse operationResponse) {
        ComponentReference requestBody = operation.getRequestBody();
        if (requestBody != null) {
            ModelSchemaResponse requestBodyResult = buildRequestBodyModel(requestBody);
            operationResponse.setRequestBody(requestBodyResult);
        }
    }

    private void assignParamsToOperationResponse(Path operation, OperationResponse operationResponse) {
        List<Parameter> parameters = operation.getParameters();
        if (parameters != null && !parameters.isEmpty()) {
            List<ModelSchemaResponse> operationResponseParams = parameters.stream()
                    .map(this::buildParamModel)
                    .collect(Collectors.toList());
            List<ModelSchemaResponse> uriParams = operationResponseParams.stream()
                    .filter(operationParam -> operationParam.getSchemaType() == SchemaType.URI_PARAM)
                    .collect(Collectors.toList());
            List<ModelSchemaResponse> queryParams = operationResponseParams.stream()
                    .filter(operationParam -> operationParam.getSchemaType() == SchemaType.QUERY_PARAM)
                    .collect(Collectors.toList());
            if (!uriParams.isEmpty()) {
                operationResponse.setUriParams(uriParams);
            }
            if (!queryParams.isEmpty()) {
                operationResponse.setQueryParams(queryParams);
            }
        }
    }

    private OperationResponse buildOperationResponse(String url, String operationMethod, Path operation) {
        OperationResponse operationResponse = new OperationResponse();
        operationResponse.setUrl(url);
        operationResponse.setMethod(operationMethod.toUpperCase());
        operationResponse.setOperationId(operation.getOperationId());
        operationResponse.setSummary(operation.getSummary());
        operationResponse.setDescription(operation.getDescription());
        operationResponse.setModules(operation.getTags());
        return operationResponse;
    }

    private ModelSchemaResponse buildParamModel(Parameter parameter) {
        return buildModelSchemaResponse(
                parameter.getName(),
                convertParamToSchemaType(parameter.getIn()),
                parameter.getRequired(),
                parameter.getSchema()
        );
    }

    private ModelSchemaResponse buildRequestBodyModel(
            ComponentReference componentReference) {
        ComponentSchema componentSchema = retrieveComponent(componentReference.getRef());
        return buildModelSchemaResponse(
                null,
                SchemaType.REQUEST_BODY,
                null,
                componentSchema
        );
    }

    private ModelSchemaResponse buildResponseModel(
            Map.Entry<String, ComponentReference> responseEntry) {
        ComponentSchema componentSchema = retrieveComponent(responseEntry.getValue().getRef());
        return buildModelSchemaResponse(
                responseEntry.getKey(),
                SchemaType.RESPONSE,
                null,
                componentSchema
        );
    }

    private SchemaType convertParamToSchemaType(String schemaType) {
        switch (schemaType) {
            case QUERY_PARAM_TYPE_NAME:
                return SchemaType.QUERY_PARAM;
            case URI_PARAM_TYPE_NAME:
                return SchemaType.URI_PARAM;
            case HEADER_TYPE_NAME:
                return SchemaType.HEADER;
            default:
                throw new MetadataParamTypeNotSupported(schemaType);
        }
    }

    private void assignOperationsToModules(List<ModuleResponse> modules, List<OperationResponse> operations) {
        for (OperationResponse operation : operations) {
            List<String> moduleNames = operation.getModules();
            moduleNames.forEach(moduleName -> findModule(modules, moduleName).addOperation(operation));
        }
    }

    private ModuleResponse findModule(List<ModuleResponse> modules, String name) {
        if (name == null) {
            throw new ModuleNotFoundException("null");
        }
        return modules.stream()
                .filter(m -> name.equals(m.getName()))
                .findFirst()
                .orElseThrow(() -> new ModuleNotFoundException(name));
    }

    private ComponentSchema retrieveComponent(
            String componentReferenceString) {
        String[] componentReferenceElements = componentReferenceString.split("/");
        String componentReferenceType = componentReferenceElements[2];
        String componentReferenceIdentifier = componentReferenceElements[3];

        Component components = metadata.getComponents();
        if (SCHEMA_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            return components.getSchemas().get(componentReferenceIdentifier);
        }

        Function<Component, Map<String, Body>> getComponentFunction;
        if (REQUEST_BODY_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            getComponentFunction = Component::getRequestBodies;
        } else if (RESPONSE_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            getComponentFunction = Component::getResponses;
        } else {
            throw new OpenapiComponentNotSupportedException(componentReferenceType);
        }
        Map<String, BodyContent> content = getComponentFunction.apply(components)
                .get(componentReferenceIdentifier)
                .getContent();
        if (content == null) {
            return null;
        }
        return retrieveComponent(
                content.get(METADATA_CONTENT_TYPE).getSchema().getRef());
    }

    private ModelSchemaResponse buildModelSchemaResponse(
            String identifier,
            SchemaType schemaType,
            Boolean required,
            ComponentSchema componentSchema) {

        if (componentSchema == null) {
            componentSchema = new ComponentSchema();
        }
        ModelSchemaResponse modelSchemaResponse = new ModelSchemaResponse();

        if (componentSchema.getType() != null && componentSchema.getType().equals("array")) {
            modelSchemaResponse.setIsArray(true);
            componentSchema = retrieveComponent(componentSchema.getItems().getRef());
        } else {
            modelSchemaResponse.setIsArray(false);
        }

        if (isHeaderOrParam(schemaType)) {
            modelSchemaResponse.setName(identifier);
            modelSchemaResponse.setRequired(required);
            modelSchemaResponse.setExample(componentSchema.getExample());
            modelSchemaResponse.setMinLength(componentSchema.getMinLength());
            modelSchemaResponse.setMaxLength(componentSchema.getMaxLength());
            modelSchemaResponse.setMinimum(componentSchema.getMinimum());
            modelSchemaResponse.setMaximum(componentSchema.getMaximum());
            modelSchemaResponse.setRequired(componentSchema.getReadOnly());
            modelSchemaResponse.setWriteOnly(componentSchema.getWriteOnly());
            modelSchemaResponse.setEnums(componentSchema.getEnums());
        } else if (isResponse(schemaType)) {
            modelSchemaResponse.setHttpStatus(identifier);
        }
        modelSchemaResponse.setDescription(componentSchema.getDescription());
        modelSchemaResponse.setFormat(componentSchema.getFormat());
        modelSchemaResponse.setType(componentSchema.getType());
        modelSchemaResponse.setSchemaType(schemaType);

        Map<String, ComponentSchema> properties = componentSchema.getProperties();

        if (properties != null && !properties.isEmpty()) {
            for (Map.Entry<String, ComponentSchema> propertyEntry : properties.entrySet()) {
                String propertyEntryIdentifier = propertyEntry.getKey();
                boolean propertyEntryRequired = false;
                if (componentSchema.getRequired() != null) {
                    propertyEntryRequired = componentSchema.getRequired().contains(propertyEntryIdentifier);
                }
                ModelSchemaResponse propertyEntryModelSchemaResponse =
                        buildModelSchemaResponse(
                                propertyEntryIdentifier,
                                SchemaType.FIELD,
                                propertyEntryRequired,
                                propertyEntry.getValue()
                        );
                modelSchemaResponse.addParameter(propertyEntryModelSchemaResponse);
            }
        }
        return modelSchemaResponse;
    }

    private boolean isHeaderOrParam(SchemaType schemaType) {
        return schemaType == SchemaType.HEADER
                || schemaType == SchemaType.QUERY_PARAM
                || schemaType == SchemaType.URI_PARAM
                || schemaType == SchemaType.FIELD;
    }

    private boolean isResponse(SchemaType schemaType) {
        return schemaType == SchemaType.RESPONSE;
    }

    private List<ModuleResponse> buildModulesResponse() {
        return metadata.getTags().stream()
                .map(tag -> new ModuleResponse(tag.getName(), tag.getDescription()))
                .collect(Collectors.toList());
    }
}
