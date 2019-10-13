package com.adamkorzeniak.masterdata.features.metadata.service;

import com.adamkorzeniak.masterdata.exception.exceptions.MetadataParamTypeNotSupported;
import com.adamkorzeniak.masterdata.exception.exceptions.MetadataResourceException;
import com.adamkorzeniak.masterdata.exception.exceptions.ModuleNotFoundException;
import com.adamkorzeniak.masterdata.exception.exceptions.OpenapiComponentNotSupportedException;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.*;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.Module;
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

    private final String FILE_PATH;

    private static final String METADATA_CONTENT_TYPE = "application/json";

    private static final String SCHEMA_COMPONENT_REFERENCE_TYPE = "schemas";
    private static final String REQUEST_BODY_COMPONENT_REFERENCE_TYPE = "requestBodies";
    private static final String RESPONSE_COMPONENT_REFERENCE_TYPE = "responses";

    private static final String URI_PARAM_TYPE_NAME = "path";
    private static final String QUERY_PARAM_TYPE_NAME = "query";
    private static final String HEADER_TYPE_NAME = "header";

    private OpenapiMetadata openAPIMetatada;

    public MetadataServiceImpl(@Value("${api.yaml.file}") String openapiPath) {
        this.FILE_PATH = openapiPath;
    }

    @Override
    public Metadata buildMetadataResponse() {
        openAPIMetatada = readMetadataFromFile();
        List<Module> modules = buildModulesResponse();
        List<Operation> operations = buildOperationResponse();
        assignOperationsToModules(modules, operations);
        return new Metadata(modules);
    }

    private OpenapiMetadata readMetadataFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is;
        try {
            File file = ResourceUtils.getFile(FILE_PATH);
            is = new FileInputStream(file);
            return mapper.readValue(is, OpenapiMetadata.class);
        } catch (IOException e) {
            throw new MetadataResourceException();
        }
    }

    private List<Operation> buildOperationResponse() {
        Map<String, Map<String, OpenapiPath>> paths = openAPIMetatada.getPaths();
        List<Operation> operationRespons = new ArrayList<>();

        for (Map.Entry<String, Map<String, OpenapiPath>> pathEntry : paths.entrySet()) {
            String url = pathEntry.getKey();

            for (Map.Entry<String, OpenapiPath> operationEntry : pathEntry.getValue().entrySet()) {
                OpenapiPath operation = operationEntry.getValue();

                Operation operationResponse = buildOperationResponse(
                        url,
                        operationEntry.getKey(),
                        operation);

                assignParamsToOperationResponse(operation, operationResponse);
                assignRequestBodyToOperationResponse(operation, operationResponse);
                assignResponsesToOperationResponse(operation, operationResponse);

                operationRespons.add(operationResponse);
            }
        }
        return operationRespons;
    }

    private void assignResponsesToOperationResponse(OpenapiPath operation, Operation operationResponse) {
        Map<String, OpenapiComponentReference> responses = operation.getResponses();
        if (responses != null && !responses.isEmpty()) {
            List<ModelSchema> responsesResult = responses.entrySet().stream()
                    .map(this::buildResponseModel)
                    .collect(Collectors.toList());
            operationResponse.setResponses(responsesResult);
        }
    }

    private void assignRequestBodyToOperationResponse(OpenapiPath operation, Operation operationResponse) {
        OpenapiComponentReference requestBody = operation.getRequestBody();
        if (requestBody != null) {
            ModelSchema requestBodyResult = buildRequestBodyModel(requestBody);
            operationResponse.setRequestBody(requestBodyResult);
        }
    }

    private void assignParamsToOperationResponse(OpenapiPath operation, Operation operationResponse) {
        List<OpenapiParameter> openapiParameters = operation.getParameters();
        if (openapiParameters != null && !openapiParameters.isEmpty()) {
            List<ModelSchema> operationResponseParams = openapiParameters.stream()
                    .map(this::buildParamModel)
                    .collect(Collectors.toList());
            List<ModelSchema> uriParams = operationResponseParams.stream()
                    .filter(operationParam -> operationParam.getSchemaType() == SchemaType.URI_PARAM)
                    .collect(Collectors.toList());
            List<ModelSchema> queryParams = operationResponseParams.stream()
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

    private Operation buildOperationResponse(String url, String operationMethod, OpenapiPath operation) {
        Operation operationResponse = new Operation();
        operationResponse.setUrl(url);
        operationResponse.setMethod(operationMethod.toUpperCase());
        operationResponse.setOperationId(operation.getOperationId());
        operationResponse.setSummary(operation.getSummary());
        operationResponse.setDescription(operation.getDescription());
        operationResponse.setModules(operation.getTags());
        return operationResponse;
    }

    private ModelSchema buildParamModel(OpenapiParameter openapiParameter) {
        return buildModelSchemaResponse(
                openapiParameter.getName(),
                convertParamToSchemaType(openapiParameter.getIn()),
                openapiParameter.getRequired(),
                openapiParameter.getSchema()
        );
    }

    private ModelSchema buildRequestBodyModel(
            OpenapiComponentReference openapiComponentReference) {
        OpenapiSchemaComponent openapiSchemaComponent = retrieveComponent(openapiComponentReference.getRef());
        return buildModelSchemaResponse(
                null,
                SchemaType.REQUEST_BODY,
                null,
                openapiSchemaComponent
        );
    }

    private ModelSchema buildResponseModel(
            Map.Entry<String, OpenapiComponentReference> responseEntry) {
        OpenapiSchemaComponent openapiSchemaComponent = retrieveComponent(responseEntry.getValue().getRef());
        return buildModelSchemaResponse(
                responseEntry.getKey(),
                SchemaType.RESPONSE,
                null,
                openapiSchemaComponent
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

    private void assignOperationsToModules(List<Module> modules, List<Operation> operations) {
        for (Operation operation : operations) {
            List<String> moduleNames = operation.getModules();
            moduleNames.forEach(moduleName -> findModule(modules, moduleName).addOperation(operation));
        }
    }

    private Module findModule(List<Module> modules, String name) {
        if (name == null) {
            throw new ModuleNotFoundException("null");
        }
        return modules.stream()
                .filter(m -> name.equals(m.getName()))
                .findFirst()
                .orElseThrow(() -> new ModuleNotFoundException(name));
    }

    private OpenapiSchemaComponent retrieveComponent(
            String componentReferenceString) {
        String[] componentReferenceElements = componentReferenceString.split("/");
        String componentReferenceType = componentReferenceElements[2];
        String componentReferenceIdentifier = componentReferenceElements[3];

        OpenapiComponent components = openAPIMetatada.getComponents();
        if (SCHEMA_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            return components.getSchemas().get(componentReferenceIdentifier);
        }

        Function<OpenapiComponent, Map<String, OpenapiContentComponent>> getComponentFunction;
        if (REQUEST_BODY_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            getComponentFunction = OpenapiComponent::getRequestBodies;
        } else if (RESPONSE_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            getComponentFunction = OpenapiComponent::getResponses;
        } else {
            throw new OpenapiComponentNotSupportedException(componentReferenceType);
        }
        Map<String, OpenapiContentValue> content = getComponentFunction.apply(components)
                .get(componentReferenceIdentifier)
                .getContent();
        if (content == null) {
            return null;
        }
        return retrieveComponent(
                content.get(METADATA_CONTENT_TYPE).getSchema().getRef());
    }

    private ModelSchema buildModelSchemaResponse(
            String identifier,
            SchemaType schemaType,
            Boolean required,
            OpenapiSchemaComponent openapiSchemaComponent) {

        ModelSchema modelSchema = new ModelSchema();

        if (openapiSchemaComponent.getType() != null && openapiSchemaComponent.getType().equals("array")) {
            modelSchema.setIsArray(true);
            openapiSchemaComponent = retrieveComponent(openapiSchemaComponent.getItems().getRef());
        } else {
            modelSchema.setIsArray(false);
        }

        if (isHeaderOrParam(schemaType)) {
            modelSchema.setName(identifier);
            modelSchema.setRequired(required);
            if (openapiSchemaComponent != null) {
                modelSchema.setExample(openapiSchemaComponent.getExample());
                modelSchema.setMinLength(openapiSchemaComponent.getMinLength());
                modelSchema.setMaxLength(openapiSchemaComponent.getMaxLength());
                modelSchema.setMinimum(openapiSchemaComponent.getMinimum());
                modelSchema.setMaximum(openapiSchemaComponent.getMaximum());
                modelSchema.setReadOnly(openapiSchemaComponent.getReadOnly());
                modelSchema.setWriteOnly(openapiSchemaComponent.getWriteOnly());
                modelSchema.setEnums(openapiSchemaComponent.getEnums());
            }
        } else if (isResponse(schemaType)) {
            modelSchema.setHttpStatus(identifier);
        }


        if (openapiSchemaComponent != null) {
            modelSchema.setDescription(openapiSchemaComponent.getDescription());
            modelSchema.setFormat(openapiSchemaComponent.getFormat());
            modelSchema.setType(openapiSchemaComponent.getType());
        }
        modelSchema.setSchemaType(schemaType);

        if (openapiSchemaComponent != null
                && openapiSchemaComponent.getProperties() != null
                && !openapiSchemaComponent.getProperties().isEmpty()) {
            Map<String, OpenapiSchemaComponent> properties = openapiSchemaComponent.getProperties();
            for (Map.Entry<String, OpenapiSchemaComponent> propertyEntry : properties.entrySet()) {
                String propertyEntryIdentifier = propertyEntry.getKey();
                boolean propertyEntryRequired = false;
                if (openapiSchemaComponent.getRequired() != null) {
                    propertyEntryRequired = openapiSchemaComponent.getRequired().contains(propertyEntryIdentifier);
                }
                ModelSchema propertyEntryModelSchema =
                        buildModelSchemaResponse(
                                propertyEntryIdentifier,
                                SchemaType.FIELD,
                                propertyEntryRequired,
                                propertyEntry.getValue()
                        );
                modelSchema.addParameter(propertyEntryModelSchema);
            }
        }
        return modelSchema;
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

    private List<Module> buildModulesResponse() {
        return openAPIMetatada.getTags().stream()
                .map(tag -> new Module(tag.getName(), tag.getDescription()))
                .collect(Collectors.toList());
    }
}
