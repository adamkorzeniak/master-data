package com.adamkorzeniak.masterdata.features.user;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasKey;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import com.adamkorzeniak.masterdata.features.user.model.Role;
import com.adamkorzeniak.masterdata.features.user.model.dto.UserRequest;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "integration-test")
@WithMockUser(username = "admin", password = "admin")
@Sql({"/sql/test-user-data.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    private static final String REGISTER_USER_PATH = "/v0/User/register";
    private static final String GET_ME_PATH = "/v0/User/me";

    private static final String CONTENT_TYPE_PARAM_NAME = "Content-Type";
    private static final String CONTENT_TYPE_PARAM_VALUE = "application/json";

    private static final String ROOT_OBJECT = "$";
    private static final String ID_FIELD = "id";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String ROLE_FIELD = "role";
    private static final String ERROR_CODE_FIELD = "code";
    private static final String ERROR_TITLE_FIELD = "title";
    private static final String ERROR_MESSAGE_FIELD = "message";

    private static final String EXISTING_USER_USERNAME = "admin";
    private static final String EXISTING_USER_PASSWORD = "admin";
    private static final Role EXISTING_USER_ROLE = Role.USER;

    private static final String NEW_USER_USERNAME = "adam";
    private static final String NEW_USER_PASSWORD = "admin";
    private static final Role NEW_USER_ROLE = Role.USER;

    private static final String INVALID_ROLE_STRING = "SUPER_ADMIN";

    private static final String BAD_REQUEST_ERROR_CODE = "REQ000";
    private static final String BAD_REQUEST_ERROR_TITLE = "Bad Request";
    private static final String USERNAME_FIELD_MISSING_ERROR_MESSAGE =
            "Invalid 'username' field value: null. Field 'username' must not be empty.";
    private static final String PASSWORD_FIELD_MISSING_ERROR_MESSAGE =
            "Invalid 'password' field value: null. Field 'password' must not be empty.";
    private static final String ROLE_FIELD_MISSING_ERROR_MESSAGE =
            "Invalid 'role' field value: null. Field 'role' is not valid.";
    private static final String ROLE_FIELD_INVALID_ERROR_MESSAGE =
            String.format("Invalid 'role' field value: %s. Field 'role' is not valid.", INVALID_ROLE_STRING);
    private static final String ALL_USER_FIELDS_MISSING_ERROR_MESSAGE =
            String.format("%s%n%s%n%s",
                    USERNAME_FIELD_MISSING_ERROR_MESSAGE,
                    ROLE_FIELD_MISSING_ERROR_MESSAGE,
                    PASSWORD_FIELD_MISSING_ERROR_MESSAGE);

    private static final String DUPLICATED_USER_ERROR_CODE = "REQ200";
    private static final String DUPLICATE_USER_ERROR_TITLE = "Duplicate Entry";
    private static final String DUPLICATED_USER_ERROR_MESSAGE =
            String.format("User with username '%s' already exists", EXISTING_USER_USERNAME);


    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void GetUserDetails_NoIssues_ReturnUserDetails() {

        when()
                .get(GET_ME_PATH)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(ID_FIELD, notNullValue())
                .body(USERNAME_FIELD, equalTo(EXISTING_USER_USERNAME))
                .body(ROOT_OBJECT, not(hasKey(PASSWORD_FIELD)))
                .body(ROLE_FIELD, equalTo(EXISTING_USER_ROLE.toString()));
    }

    @Test
    public void RegisterUser_NoIssues_ReturnsAddedUser() {

        UserRequest requestBody = new UserRequest();
        requestBody.setUsername(NEW_USER_USERNAME);
        requestBody.setPassword(NEW_USER_PASSWORD);
        requestBody.setRole(NEW_USER_ROLE.toString());

        given()
                .header(CONTENT_TYPE_PARAM_NAME, CONTENT_TYPE_PARAM_VALUE)
                .body(requestBody)
                .when()
                .post(REGISTER_USER_PATH)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(ID_FIELD, notNullValue())
                .body(USERNAME_FIELD, equalTo(NEW_USER_USERNAME))
                .body(ROOT_OBJECT, not(hasKey(PASSWORD_FIELD)))
                .body(ROLE_FIELD, equalTo(NEW_USER_ROLE.toString()));
    }

    @Test
    public void RegisterUser_UserAlreadyExists_ReturnConflictErrorResponse() {
        UserRequest requestBody = new UserRequest();
        requestBody.setUsername(EXISTING_USER_USERNAME);
        requestBody.setPassword(EXISTING_USER_PASSWORD);
        requestBody.setRole(EXISTING_USER_ROLE.toString());

        given()
                .header(CONTENT_TYPE_PARAM_NAME, CONTENT_TYPE_PARAM_VALUE)
                .body(requestBody)
                .when()
                .post(REGISTER_USER_PATH)
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(ID_FIELD, nullValue())
                .body(ERROR_CODE_FIELD, equalTo(DUPLICATED_USER_ERROR_CODE))
                .body(ERROR_TITLE_FIELD, equalTo(DUPLICATE_USER_ERROR_TITLE))
                .body(ERROR_MESSAGE_FIELD, equalTo(DUPLICATED_USER_ERROR_MESSAGE));
    }

    @Test
    public void RegisterUser_AllFieldsMissing_ReturnsValidationErrorResponse() {

        UserRequest requestBody = new UserRequest();

        given()
                .header(CONTENT_TYPE_PARAM_NAME, CONTENT_TYPE_PARAM_VALUE)
                .body(requestBody)
                .when()
                .post(REGISTER_USER_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ID_FIELD, nullValue())
                .body(ERROR_CODE_FIELD, equalTo(BAD_REQUEST_ERROR_CODE))
                .body(ERROR_TITLE_FIELD, equalTo(BAD_REQUEST_ERROR_TITLE))
                .body(ERROR_MESSAGE_FIELD, equalTo(ALL_USER_FIELDS_MISSING_ERROR_MESSAGE));
    }

    @Test
    public void RegisterUser_UsernameMissing_ReturnsValidationErrorResponse() {

        UserRequest requestBody = new UserRequest();
        requestBody.setPassword(NEW_USER_PASSWORD);
        requestBody.setRole(NEW_USER_ROLE.toString());

        given()
                .header(CONTENT_TYPE_PARAM_NAME, CONTENT_TYPE_PARAM_VALUE)
                .body(requestBody)
                .when()
                .post(REGISTER_USER_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ID_FIELD, nullValue())
                .body(ERROR_CODE_FIELD, equalTo(BAD_REQUEST_ERROR_CODE))
                .body(ERROR_TITLE_FIELD, equalTo(BAD_REQUEST_ERROR_TITLE))
                .body(ERROR_MESSAGE_FIELD, equalTo(USERNAME_FIELD_MISSING_ERROR_MESSAGE));
    }

    @Test
    public void RegisterUser_PasswordMissing_ReturnsValidationErrorResponse() {

        UserRequest requestBody = new UserRequest();
        requestBody.setUsername(NEW_USER_USERNAME);
        requestBody.setRole(NEW_USER_ROLE.toString());

        given()
                .header(CONTENT_TYPE_PARAM_NAME, CONTENT_TYPE_PARAM_VALUE)
                .body(requestBody)
                .when()
                .post(REGISTER_USER_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ID_FIELD, nullValue())
                .body(ERROR_CODE_FIELD, equalTo(BAD_REQUEST_ERROR_CODE))
                .body(ERROR_TITLE_FIELD, equalTo(BAD_REQUEST_ERROR_TITLE))
                .body(ERROR_MESSAGE_FIELD, equalTo(PASSWORD_FIELD_MISSING_ERROR_MESSAGE));
    }

    @Test
    public void RegisterUser_RoleMissing_ReturnsValidationErrorResponse() {

        UserRequest requestBody = new UserRequest();
        requestBody.setUsername(NEW_USER_USERNAME);
        requestBody.setPassword(NEW_USER_PASSWORD);

        given()
                .header(CONTENT_TYPE_PARAM_NAME, CONTENT_TYPE_PARAM_VALUE)
                .body(requestBody)
                .when()
                .post(REGISTER_USER_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ID_FIELD, nullValue())
                .body(ERROR_CODE_FIELD, equalTo(BAD_REQUEST_ERROR_CODE))
                .body(ERROR_TITLE_FIELD, equalTo(BAD_REQUEST_ERROR_TITLE))
                .body(ERROR_MESSAGE_FIELD, equalTo(ROLE_FIELD_MISSING_ERROR_MESSAGE));
    }

    @Test
    public void RegisterUser_RoleInvalid_ReturnsValidationErrorResponse() {

        UserRequest requestBody = new UserRequest();
        requestBody.setUsername(NEW_USER_USERNAME);
        requestBody.setPassword(NEW_USER_PASSWORD);
        requestBody.setRole(INVALID_ROLE_STRING);

        given()
                .header(CONTENT_TYPE_PARAM_NAME, CONTENT_TYPE_PARAM_VALUE)
                .body(requestBody)
                .when()
                .post(REGISTER_USER_PATH)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(ID_FIELD, nullValue())
                .body(ERROR_CODE_FIELD, equalTo(BAD_REQUEST_ERROR_CODE))
                .body(ERROR_TITLE_FIELD, equalTo(BAD_REQUEST_ERROR_TITLE))
                .body(ERROR_MESSAGE_FIELD, equalTo(ROLE_FIELD_INVALID_ERROR_MESSAGE));
    }
}
