package com.adamkorzeniak.masterdata;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "integration-test")
@WithMockUser(username = "admin", password = "admin")
@SpringBootTest
public class IntegrationTest {

    private static final String GET_ME_PATH = "/v0/User/me";
    private static final String REGISTER_USER_PATH = "/v0/User/register";
    private static final String DUMMY_PATH = "/v0/dummy";

    private static final String CONTENT_TYPE_PARAM_NAME = "Content-Type";
    private static final String CONTENT_TYPE_PARAM_INVALID_VALUE = "application/xml";
    private static final String ACCEPT_PARAM_NAME = "Accept";
    private static final String ACCEPT_PARAM_INVALID_VALUE = "Accept";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void RequestSend_NotFoundPath_ReturnsNotFoundErrorResponse() {

        when()
            .get(DUMMY_PATH)
        .then()
            .statusCode(404);
    }

    @Test
    public void RequestSend_MethodNotAllowed_ReturnsNotAllowedErrorResponse() {

        when()
            .get(REGISTER_USER_PATH)
        .then()
            .statusCode(405);
    }

    @Test
    public void RequestSend_AcceptHeaderInvalid_ReturnsErrorResponse() {

        given()
            .header(ACCEPT_PARAM_NAME, ACCEPT_PARAM_INVALID_VALUE)
        .when()
            .get(GET_ME_PATH)
        .then()
            .statusCode(406);
    }
}
