package com.adamkorzeniak.masterdata.features.metadata;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "integration-test")
@WithMockUser(username = "admin", password = "admin")
@Sql({"/sql/test-user-data.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MetadataFunctionalTest {

    private static final String METADATA_PATH = "/v0/Metadata";
    private static final String MODULES_FIELD_NAME = "modules";
    private static final int MODULES_SIZE = 3;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvcResponse metadataMockMvcResponse;

    @Test
    public void RealOpenApiDefinition_GetMetadataResponse_ContainsThreeModules() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        when().get(METADATA_PATH)
                .then()
                .statusCode(200)
                .body(MODULES_FIELD_NAME, notNullValue())
                .body(MODULES_FIELD_NAME, hasSize(MODULES_SIZE));
    }
}
