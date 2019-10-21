package com.adamkorzeniak.masterdata.features.metadata.service;

import com.adamkorzeniak.masterdata.exception.exceptions.MetadataParamTypeNotSupported;
import com.adamkorzeniak.masterdata.exception.exceptions.MetadataResourceException;
import com.adamkorzeniak.masterdata.exception.exceptions.ModuleNotFoundException;
import com.adamkorzeniak.masterdata.exception.exceptions.OpenapiComponentNotSupportedException;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.Module;
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

    private final String OPEN_API_FILE;

    private static final String METADATA_CONTENT_TYPE = "application/json";

    private static final String SCHEMA_COMPONENT_REFERENCE_TYPE = "schemas";
    private static final String REQUEST_BODY_COMPONENT_REFERENCE_TYPE = "requestBodies";
    private static final String RESPONSE_COMPONENT_REFERENCE_TYPE = "responses";

    private static final String URI_PARAM_TYPE_NAME = "path";
    private static final String QUERY_PARAM_TYPE_NAME = "query";
    private static final String HEADER_TYPE_NAME = "header";

    private OpenapiMetadata openAPIMetadata;

    public MetadataServiceImpl(@Value("${api.yaml.file}") String openapiPath) {
        this.OPEN_API_FILE = openapiPath;
    }

    @Override
    public Metadata buildMetadata() {
        openAPIMetadata = readMetadataFromOpenapiFile();
        List<Module> modules = buildModules(openAPIMetadata.getTags());
        List<Operation> operations = buildOperations(openAPIMetadata.getPaths());
        assignOperationsToModules(modules, operations);
        return new Metadata(modules);
    }

    private OpenapiMetadata readMetadataFromOpenapiFile() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is;
        try {
            File file = ResourceUtils.getFile(OPEN_API_FILE);
            is = new FileInputStream(file);
            return mapper.readValue(is, OpenapiMetadata.class);
        } catch (IOException e) {
            throw new MetadataResourceException();
        }
    }

    private List<Operation> buildOperations(Map<String, Map<String, OpenapiPath>> paths) {
        List<Operation> operations = new ArrayList<>();
        for (Map.Entry<String, Map<String, OpenapiPath>> pathEntry : paths.entrySet()) {
            for (Map.Entry<String, OpenapiPath> operationEntry : pathEntry.getValue().entrySet()) {
                Operation operation = buildOperation(
                        pathEntry.getKey(),
                        operationEntry.getKey(),
                        operationEntry.getValue());
                operations.add(operation);
            }
        }
        return operations;
    }

    private Operation buildOperation(String url, String method, OpenapiPath openapiOperation) {
        Operation operation = buildOperations(
                url,
                method,
                openapiOperation);

        assignParamsToOperation(operation, openapiOperation);
        assignRequestBodyToOperation(operation, openapiOperation);
        assignResponsesToOperation(operation, openapiOperation);
        return operation;
    }

    private void assignResponsesToOperation(Operation operationResult, OpenapiPath openapiOperation) {
        Map<String, OpenapiComponentReference> responses = openapiOperation.getResponses();
        if (responses != null && !responses.isEmpty()) {
            List<ModelSchema> responsesResult = responses.entrySet().stream()
                    .map(this::buildResponseModel)
                    .collect(Collectors.toList());
            operationResult.setResponses(responsesResult);
        }
    }

    private void assignRequestBodyToOperation(Operation operationResult, OpenapiPath openapiOperation) {
        OpenapiComponentReference requestBody = openapiOperation.getRequestBody();
        if (requestBody != null) {
            ModelSchema requestBodyResult = buildRequestBodyModel(requestBody);
            operationResult.setRequestBody(requestBodyResult);
        }
    }

    private void assignParamsToOperation(Operation operationResult, OpenapiPath openapiOperation) {
        List<OpenapiParameter> openapiParameters = openapiOperation.getParameters();
        if (openapiParameters != null && !openapiParameters.isEmpty()) {
            List<ModelSchema> operationParams = openapiParameters.stream()
                    .map(this::buildParamModel)
                    .collect(Collectors.toList());
            List<ModelSchema> uriParams = operationParams.stream()
                    .filter(operationParam -> operationParam.getSchemaType() == SchemaType.URI_PARAM)
                    .collect(Collectors.toList());
            List<ModelSchema> queryParams = operationParams.stream()
                    .filter(operationParam -> operationParam.getSchemaType() == SchemaType.QUERY_PARAM)
                    .collect(Collectors.toList());
            operationResult.setUriParams(uriParams);
            operationResult.setQueryParams(queryParams);
        }
    }

    private Operation buildOperations(String url, String operationMethod, OpenapiPath openapiOperation) {
        Operation operation = new Operation();
        operation.setUrl(url);
        operation.setMethod(operationMethod.toUpperCase());
        operation.setOperationId(openapiOperation.getOperationId());
        operation.setSummary(openapiOperation.getSummary());
        operation.setDescription(openapiOperation.getDescription());
        operation.setModules(openapiOperation.getTags());
        return operation;
    }

    private ModelSchema buildParamModel(OpenapiParameter openapiParameter) {
        return buildModelSchema(
                openapiParameter.getName(),
                convertParamToSchemaType(openapiParameter.getIn()),
                openapiParameter.getRequired(),
                openapiParameter.getSchema()
        );
    }

    private ModelSchema buildRequestBodyModel(OpenapiComponentReference openapiComponentReference) {
        OpenapiSchemaComponent openapiSchemaComponent = retrieveComponent(openapiComponentReference.getRef());
        return buildModelSchema(
                "request-body",
                SchemaType.REQUEST_BODY,
                true,
                openapiSchemaComponent
        );
    }

    private ModelSchema buildResponseModel(Map.Entry<String, OpenapiComponentReference> responseEntry) {
        OpenapiSchemaComponent openapiSchemaComponent = retrieveComponent(responseEntry.getValue().getRef());
        return buildModelSchema(
                responseEntry.getKey(),
                SchemaType.RESPONSE,
                false,
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

        OpenapiComponent components = openAPIMetadata.getComponents();
        String[] componentReferenceElements = componentReferenceString.split("/");
        String componentReferenceType = componentReferenceElements[2];
        String componentReferenceIdentifier = componentReferenceElements[3];
        String description = null;

        if (!SCHEMA_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            OpenapiContentComponent contentComponent = retrieveOpenapiContentComponent(components, componentReferenceType, componentReferenceIdentifier);
            if (contentComponent.getContent() == null) {
                return null;
            }
            componentReferenceString = contentComponent.getContent().get(METADATA_CONTENT_TYPE).getSchema().getRef();
            componentReferenceIdentifier = componentReferenceString.split("/")[3];
            description = contentComponent.getDescription();
        }

        OpenapiSchemaComponent openapiSchemaComponent = components.getSchemas().get(componentReferenceIdentifier);
        if (openapiSchemaComponent != null && description != null) {
            openapiSchemaComponent.setDescription(description);
        }
        return openapiSchemaComponent;
    }

    private OpenapiContentComponent retrieveOpenapiContentComponent(OpenapiComponent components, String componentReferenceType, String componentReferenceIdentifier) {
        Function<OpenapiComponent, Map<String, OpenapiContentComponent>> getComponentFunction;
        if (REQUEST_BODY_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            getComponentFunction = OpenapiComponent::getRequestBodies;
        } else if (RESPONSE_COMPONENT_REFERENCE_TYPE.equals(componentReferenceType)) {
            getComponentFunction = OpenapiComponent::getResponses;
        } else {
            throw new OpenapiComponentNotSupportedException(componentReferenceType);
        }
        return getComponentFunction.apply(components)
                .get(componentReferenceIdentifier);
    }

    private ModelSchema buildModelSchema(
            String identifier,
            SchemaType schemaType,
            Boolean required,
            OpenapiSchemaComponent openapiSchemaComponent) {

        ModelSchema modelSchema = new ModelSchema();

        if (openapiSchemaComponent != null
                && openapiSchemaComponent.getType() != null
                && openapiSchemaComponent.getType().equals("array")) {
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
                if (openapiSchemaComponent.getEnums() != null) {
                    modelSchema.setEnums(openapiSchemaComponent.getEnums());
                }
            }
        } else if (SchemaType.RESPONSE == schemaType) {
            modelSchema.setHttpStatus(identifier);
        }

        if (openapiSchemaComponent != null) {
            modelSchema.setDescription(openapiSchemaComponent.getDescription());
            modelSchema.setFormat(openapiSchemaComponent.getFormat());
            modelSchema.setType(openapiSchemaComponent.getType());
        }
        modelSchema.setSchemaType(schemaType);

        if (hasProperties(openapiSchemaComponent)) {
            addParametersToModelSchema(
                    modelSchema,
                    openapiSchemaComponent.getProperties(),
                    openapiSchemaComponent.getRequired());
        }
        return modelSchema;
    }

    private void addParametersToModelSchema(
            ModelSchema modelSchema,
            Map<String, OpenapiSchemaComponent> properties,
            List<String> requiredProperties) {
        for (Map.Entry<String, OpenapiSchemaComponent> propertyEntry : properties.entrySet()) {
            String propertyEntryIdentifier = propertyEntry.getKey();
            boolean propertyEntryRequired = requiredProperties != null && requiredProperties.contains(propertyEntryIdentifier);
            ModelSchema propertyEntryModelSchema =
                    buildModelSchema(
                            propertyEntryIdentifier,
                            SchemaType.FIELD,
                            propertyEntryRequired,
                            propertyEntry.getValue()
                    );
            modelSchema.addParameter(propertyEntryModelSchema);
        }
    }

    private boolean hasProperties(OpenapiSchemaComponent openapiSchemaComponent) {
        return openapiSchemaComponent != null
                && openapiSchemaComponent.getProperties() != null
                && !openapiSchemaComponent.getProperties().isEmpty();
    }

    private boolean isHeaderOrParam(SchemaType schemaType) {
        return schemaType == SchemaType.HEADER
                || schemaType == SchemaType.QUERY_PARAM
                || schemaType == SchemaType.URI_PARAM
                || schemaType == SchemaType.FIELD;
    }

    private List<Module> buildModules(List<OpenapiTag> tags) {
        return tags.stream()
                .map(tag -> new Module(tag.getName(), tag.getDescription()))
                .collect(Collectors.toList());
    }
}
