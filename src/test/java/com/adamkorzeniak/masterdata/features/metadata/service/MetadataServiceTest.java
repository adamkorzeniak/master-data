package com.adamkorzeniak.masterdata.features.metadata.service;

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

    private MetadataResponse metadataResponse;

    @Autowired
    private MetadataService metadataService;

    @BeforeEach
    public void setupMetadataResponseForValidation() {
        metadataResponse = metadataService.buildMetadataResponse();
    }

    @Test
    public void RealOpenApiDefinition_GetMetadataResponse_ContainsThreeModules() {
        List<ModuleResponse> modules = metadataResponse.getModules();
        assertThat(modules).isNotNull();
        assertThat(modules).hasSize(MODULES_SIZE);

        for (ModuleResponse module : modules) {

            assertStringIsUpperCase(module.getName());
            assertStringIsUpperCase(module.getDescription());

            List<OperationResponse> operations = module.getOperations();

            assertThat(operations).isNotNull();
            assertThat(operations).isNotEmpty();

            for (OperationResponse operation : operations) {

                assertThat(operation.getUrl()).isNotNull();
                assertThat(operation.getUrl()).startsWith("/");
                assertThat(operation.getUrl()).doesNotEndWith("/");

                assertThat(operation.getMethod()).isNotNull();
                assertThat(operation.getMethod()).isIn(getSupportedHTTPMethods());

                assertStringIsLowerCase(operation.getOperationId());
                assertStringIsUpperCase(operation.getSummary());
                assertStringIsUpperCase(operation.getDescription());

                List<ModelSchemaResponse> queryParams = operation.getQueryParams();
                if (queryParams != null) {
                    assertNoDuplicates(queryParams, SchemaType.QUERY_PARAM);
                    queryParams.forEach(this::assertQueryParamIsValid);
                }

                List<ModelSchemaResponse> uriParams = operation.getUriParams();
                assertUriParamsMatchUrl(operation.getUrl(), uriParams);
                if (uriParams != null) {
                    assertNoDuplicates(uriParams, SchemaType.URI_PARAM);
                    uriParams.forEach(this::assertUriParamIsValid);
                }

                ModelSchemaResponse requestBody = operation.getRequestBody();
                if (requestBody != null) {
                    assertRequestBodyIsValid(requestBody);
                }

                List<ModelSchemaResponse> responses = operation.getResponses();
                if (responses != null) {
                    System.out.println("no dup " + operation.getUrl() + operation.getMethod());
                    assertNoDuplicates(responses, SchemaType.RESPONSE);
                    System.out.println("for each response " + operation.getUrl() + operation.getMethod());
                    responses.forEach(this::assertResponseIsValid);
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

    private void assertQueryParamIsValid(ModelSchemaResponse queryParam) {
        assertThat(queryParam).isNotNull();

        assertStringIsLowerCase(queryParam.getName());
        assertThat(queryParam.getHttpStatus()).isNull();
//        assertStringIsUpperCase(queryParam.getDescription());
//        assertThat(queryParam.getRequired()).isNotNull();
        String type = queryParam.getType();
//        assertThat(type).isIn(List.of("string", "integer"));
        assertThat(queryParam.getExample()).isNotEmpty();
        assertThat(queryParam.getWriteOnly()).isNull();
        assertThat(queryParam.getReadOnly()).isNull();
        assertThat(queryParam.getParameters()).isNull();

        if (type.equals("string")) {
            assertThat(queryParam.getFormat()).isNull();
            assertThat(queryParam.getMinimum()).isNull();
            assertThat(queryParam.getMaximum()).isNull();
        } else if (type.equals("integer")) {
            assertThat(queryParam.getFormat()).isIn("int32", "int64");
            assertThat(queryParam.getMinLength()).isNull();
            assertThat(queryParam.getMaxLength()).isNull();
        }

    }

    private void assertUriParamIsValid(ModelSchemaResponse uriParam) {
        assertThat(uriParam).isNotNull();

        assertStringIsLowerCase(uriParam.getName());
        assertThat(uriParam.getHttpStatus()).isNull();
//        assertStringIsUpperCase(uriParam.getDescription());
//        assertThat(uriParam.getRequired()).isNotNull();
        assertThat(uriParam.getType()).isEqualTo("integer");
        assertThat(uriParam.getFormat()).isIn("int32", "int64");
        assertThat(uriParam.getMinLength()).isNull();
        assertThat(uriParam.getMaxLength()).isNull();
        assertThat(uriParam.getExample()).isNotEmpty();
        assertThat(uriParam.getWriteOnly()).isNull();
        assertThat(uriParam.getReadOnly()).isNull();
        assertThat(uriParam.getEnums()).isNull();
        assertThat(uriParam.getParameters()).isNull();
    }

    private void assertRequestBodyIsValid(ModelSchemaResponse requestBody) {
        assertThat(requestBody).isNotNull();

        assertThat(requestBody.getName()).isNull();
        assertThat(requestBody.getHttpStatus()).isNull();
//        assertStringIsUpperCase(requestBody.getDescription());
        assertThat(requestBody.getRequired()).isNull();
        assertThat(requestBody.getType()).isIn(List.of("object", "array"));
        assertThat(requestBody.getFormat()).isNull();
        assertThat(requestBody.getExample()).isNull();
        assertThat(requestBody.getMinLength()).isNull();
        assertThat(requestBody.getMaxLength()).isNull();
        assertThat(requestBody.getMinimum()).isNull();
        assertThat(requestBody.getMaximum()).isNull();
        assertThat(requestBody.getReadOnly()).isNull();
        assertThat(requestBody.getWriteOnly()).isNull();
        assertThat(requestBody.getEnums()).isNull();

        assertThat(requestBody.getParameters()).isNotNull();
        // TODO: Check parameters
    }

    private void assertResponseIsValid(ModelSchemaResponse response) {

        System.out.println("response check " + response.getHttpStatus());
        assertThat(response).isNotNull();

        assertThat(response.getName()).isNull();

        assertThat(response.getHttpStatus()).isNotNull();
        assertThat(response.getHttpStatus()).isNotEmpty();
        assertThat(StringUtils.isNumeric(response.getHttpStatus())).isTrue();
        int httpStatus = Integer.parseInt(response.getHttpStatus());
        assertThat(httpStatus).isLessThan(600);
        assertThat(httpStatus).isGreaterThanOrEqualTo(200);

//        assertStringIsUpperCase(response.getDescription());
        assertThat(response.getRequired()).isNull();
        if (httpStatus != 204) {
            assertThat(response.getType()).isIn(List.of("object", "array"));
        } else {
            assertThat(response.getType()).isNull();
        }
        assertThat(response.getFormat()).isNull();
        assertThat(response.getExample()).isNull();
        assertThat(response.getMinLength()).isNull();
        assertThat(response.getMaxLength()).isNull();
        assertThat(response.getMinimum()).isNull();
        assertThat(response.getMaximum()).isNull();
        assertThat(response.getReadOnly()).isNull();
        assertThat(response.getWriteOnly()).isNull();
        assertThat(response.getEnums()).isNull();

        if (httpStatus != 204) {
            System.out.printf("Response status %d", httpStatus);
            assertThat(response.getParameters()).isNotNull();
            // TODO: Check parameters
        }

    }

    private void assertNoDuplicates(List<ModelSchemaResponse> elements, SchemaType type) {
        if (elements.size() < 2) {
            return;
        }
        Function<ModelSchemaResponse, String> getIdentifier =
                (type == SchemaType.RESPONSE) ?
                        ModelSchemaResponse::getHttpStatus : ModelSchemaResponse::getName;
        List<String> sortedIdentifiers = elements.stream()
                .map(getIdentifier)
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < sortedIdentifiers.size() - 1; i++) {
            assertThat(sortedIdentifiers.get(i)).isNotEqualTo(sortedIdentifiers.get(i + 1));
        }
    }

    private void assertUriParamsMatchUrl(String url, List<ModelSchemaResponse> uriParams) {
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
                .map(ModelSchemaResponse::getName)
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
