package com.adamkorzeniak.masterdata.features.metadata.service;

import com.adamkorzeniak.masterdata.features.metadata.model.dto.Module;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class MetadataServiceTest {

    private static final int MODULES_SIZE = 3;

    private Metadata metadata;

    @Autowired
    private MetadataService metadataService;

    @BeforeEach
    public void setupMetadataResponseForValidation() {
        metadata = metadataService.buildMetadata();
    }

    @Test
    public void RealOpenApiDefinition_GetMetadataResponse_ContainsThreeModules() {
        List<Module> modules = metadata.getModules();
        assertThat(modules).isNotNull();
        assertThat(modules).hasSize(MODULES_SIZE);

        for (Module module : modules) {

            assertStringIsUpperCase(module.getName());
            assertStringIsUpperCase(module.getDescription());

            List<Operation> operations = module.getOperations();

            assertThat(operations).isNotNull();
            assertThat(operations).isNotEmpty();

            for (Operation operation : operations) {

                assertThat(operation.getUrl()).isNotNull();
                assertThat(operation.getUrl()).startsWith("/");
                assertThat(operation.getUrl()).doesNotEndWith("/");

                assertThat(operation.getMethod()).isNotNull();
                assertThat(operation.getMethod()).isIn(getSupportedHTTPMethods());

                assertStringIsLowerCase(operation.getOperationId());
                assertStringIsUpperCase(operation.getSummary());
                assertStringIsUpperCase(operation.getDescription());

                List<ModelSchema> queryParams = operation.getQueryParams();
                if (queryParams != null) {
                    assertNoDuplicates(queryParams, SchemaType.QUERY_PARAM);
                    queryParams.forEach(this::assertParamIsValid);
                }

                List<ModelSchema> uriParams = operation.getUriParams();
                assertUriParamsMatchUrl(operation.getUrl(), uriParams);
                if (uriParams != null) {
                    assertNoDuplicates(uriParams, SchemaType.URI_PARAM);
                    uriParams.forEach(this::assertParamIsValid);
                }

                ModelSchema requestBody = operation.getRequestBody();
                if (requestBody != null) {
                    assertBodyIsValid(requestBody);
                }

                List<ModelSchema> responses = operation.getResponses();
                if (responses != null) {
                    assertNoDuplicates(responses, SchemaType.RESPONSE);
                    responses.forEach(this::assertBodyIsValid);
                }
            }
        }
    }

    private void assertStringIsUpperCase(String actualValue) {
        assertThat(actualValue).isNotNull();
        assertThat(actualValue).isNotEmpty();
        assertThat(actualValue.substring(0, 1)).isUpperCase();
    }

    private void assertStringIsLowerCase(String actualValue) {
        assertThat(actualValue).isNotNull();
        assertThat(actualValue).isNotEmpty();
        assertThat(actualValue.substring(0, 1)).isLowerCase();

    }

    private void assertParamIsValid(ModelSchema param) {
        assertThat(param).isNotNull();

        assertStringIsLowerCase(param.getName());
        assertThat(param.getHttpStatus()).isNull();
//        assertStringIsUpperCase(param.getDescription());
        assertThat(param.getRequired()).isNotNull();
        String type = param.getType();
        if (SchemaType.QUERY_PARAM == param.getSchemaType()) {
            assertThat(type).isIn(List.of("string", "integer", "boolean"));
        } else if (SchemaType.URI_PARAM == param.getSchemaType()) {
            assertThat(type).isEqualTo("integer");
        }

        if ("string".equals(type)) {
            assertThat(param.getFormat()).isNull();
            assertThat(param.getMinimum()).isNull();
            assertThat(param.getMaximum()).isNull();
        } else if ("integer".equals(type)) {
            assertThat(param.getFormat()).isIn("int32", "int64");
            assertThat(param.getMinLength()).isNull();
            assertThat(param.getMaxLength()).isNull();
        }

        assertThat(param.getExample()).isNotEmpty();
        assertThat(param.getWriteOnly()).isNull();
        assertThat(param.getReadOnly()).isNull();
        assertThat(param.getEnums()).isNotNull();
        assertThat(param.getParameters()).isNotNull();
        assertThat(param.getParameters()).isEmpty();
    }

    private void assertBodyIsValid(ModelSchema bodyModel) {
        assertThat(bodyModel).isNotNull();

        assertThat(bodyModel.getName()).isNull();
        if (SchemaType.RESPONSE == bodyModel.getSchemaType()) {
            assertThat(bodyModel.getHttpStatus()).isNotNull();
            assertThat(bodyModel.getHttpStatus()).isNotEmpty();
            assertThat(StringUtils.isNumeric(bodyModel.getHttpStatus())).isTrue();
            int httpStatus = Integer.parseInt(bodyModel.getHttpStatus());
            assertThat(httpStatus).isLessThan(600);
            assertThat(httpStatus).isGreaterThanOrEqualTo(200);
            if (httpStatus != 204) {
                assertThat(bodyModel.getType()).isIn(List.of("object", "array"));
            } else {
                assertThat(bodyModel.getType()).isNull();
            }

        } else {
            assertThat(bodyModel.getHttpStatus()).isNull();
            assertThat(bodyModel.getType()).isIn(List.of("object", "array"));

        }
//        assertStringIsUpperCase(bodyModel.getDescription());
        assertThat(bodyModel.getRequired()).isNull();
        assertThat(bodyModel.getFormat()).isNull();
        assertThat(bodyModel.getExample()).isNull();
        assertThat(bodyModel.getMinLength()).isNull();
        assertThat(bodyModel.getMaxLength()).isNull();
        assertThat(bodyModel.getMinimum()).isNull();
        assertThat(bodyModel.getMaximum()).isNull();
        assertThat(bodyModel.getReadOnly()).isNull();
        assertThat(bodyModel.getWriteOnly()).isNull();
        assertThat(bodyModel.getEnums()).isNotNull();
        assertThat(bodyModel.getEnums()).isEmpty();

        if (SchemaType.RESPONSE == bodyModel.getSchemaType() && "204".equals(bodyModel.getHttpStatus())) {
            assertThat(bodyModel.getParameters()).isNotNull();
        }
    }

    private void assertNoDuplicates(List<ModelSchema> elements, SchemaType type) {
        if (elements.size() < 2) {
            return;
        }
        Function<ModelSchema, String> getIdentifier =
                (type == SchemaType.RESPONSE) ?
                        ModelSchema::getHttpStatus : ModelSchema::getName;
        List<String> sortedIdentifiers = elements.stream()
                .map(getIdentifier)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < sortedIdentifiers.size() - 1; i++) {
            assertThat(sortedIdentifiers.get(i)).isNotEqualTo(sortedIdentifiers.get(i + 1));
        }
    }

    private void assertUriParamsMatchUrl(String url, List<ModelSchema> uriParams) {
        List<String> urlElements = Arrays.asList(url.split("/"));
        List<String> uriPathElements = urlElements.stream()
                .filter(this::isUriParamPathElement)
                .map(this::extractUriParamName)
                .collect(Collectors.toList());
        if (uriParams == null) {
            uriParams = new ArrayList<>();
        }
        assertThat(uriParams.size()).isEqualTo(uriPathElements.size());
        uriParams.stream()
                .map(ModelSchema::getName)
                .forEach(uriParam -> assertThat(uriPathElements).contains(uriParam));
    }

    private String extractUriParamName(String uriParamPathElement) {
        return uriParamPathElement.substring(1, uriParamPathElement.length() - 1);
    }

    private boolean isUriParamPathElement(String pathElement) {
        int pathElementLength = pathElement.length();
        if (pathElementLength < 3) {
            return false;
        }
        return pathElement.charAt(0) == '{' && pathElement.charAt(pathElementLength - 1) == '}';
    }

    private List<String> getSupportedHTTPMethods() {
        return List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE"
        );
    }
}
