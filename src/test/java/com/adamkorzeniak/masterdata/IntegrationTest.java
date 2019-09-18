package com.adamkorzeniak.masterdata;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;

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
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@BeforeEach
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
	    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}

	@Test
	public void RequestSend_NotFoundPath_ReturnsNotFoundErrorResponse() throws Exception {
		
		when()
			.get("/v0/dummy")
		.then()
			.statusCode(404); 
	}

	@Test
	public void RequestSend_MethodNotAllowed_ReturnsNotAllowedErrorResponse() throws Exception {
		
		when()
			.get("/v0/User/register")
		.then()
			.statusCode(405); 
	}
	
}
