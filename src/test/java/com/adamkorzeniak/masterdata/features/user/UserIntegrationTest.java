package com.adamkorzeniak.masterdata.features.user;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
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
	
	private static final String EXISTING_USER_USERNAME = "admin";
	private static final Role EXISTING_USER_ROLE = Role.USER;
	
	private static final String NEW_USER_USERNAME = "adam";
	private static final String NEW_USER_PASSWORD = "admin";
	private static final Role NEW_USER_ROLE = Role.USER;
	
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
			.statusCode(200)
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
			.statusCode(201)
			.body(ID_FIELD, notNullValue())
			.body(USERNAME_FIELD, equalTo(NEW_USER_USERNAME))
			.body(ROOT_OBJECT, not(hasKey(PASSWORD_FIELD)))
			.body(ROLE_FIELD, equalTo(NEW_USER_ROLE.toString())); 
	}

//	@Test
	public void RegisterUser_UserAlreadyExists_ReturnConflictErrorResponse() {
		fail();
	}
	
//	@Test
	public void RegisterUser_AllFieldsMissing_ReturnsValidationErrorResponse() {
		fail();
	}
	
//	@Test
	public void RegisterUser_UsernameMissing_ReturnsValidationErrorResponse() {
		fail();
	}
	
//	@Test
	public void RegisterUser_PasswordMissing_ReturnsValidationErrorResponse() {
		fail();
	}
	
//	@Test
	public void RegisterUser_RoleMissing_ReturnsValidationErrorResponse() {
		fail();
	}
	
//	@Test
	public void RegisterUser_RoleInvalid_ReturnsValidationErrorResponse() {
		fail();
	}
}
