package com.adamkorzeniak.masterdata.features.user.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.adamkorzeniak.masterdata.features.user.model.Role;
import com.adamkorzeniak.masterdata.features.user.model.User;
import com.adamkorzeniak.masterdata.features.user.model.dto.UserRequest;
import com.adamkorzeniak.masterdata.features.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class UserControllerTest {

	private static final String BASE_PATH = "/v0/User";
	private static final String REGISTER_PATH = "/register";
	private static final String GET_ME_PATH = "/me";

	private static final Long ID = 123l;
	private static final String USERNAME = "adam";
	private static final String PASSWORD = "password";
	private static final Role ROLE = Role.USER;

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void AddUser_NoIssues_ReturnsAddedUser() throws Exception {
		UserRequest postUser = new UserRequest();
		postUser.setUsername(USERNAME);
		postUser.setPassword(PASSWORD);
		postUser.setRole(ROLE.toString());
		User mockUser = new User();
		mockUser.setId(ID);
		mockUser.setUsername(USERNAME);
		mockUser.setPassword(PASSWORD);
		mockUser.setRole(ROLE);
		
	    String requestJson = convertToJson(postUser);

		when(userService.register(ArgumentMatchers.any())).thenReturn(mockUser);
		
		mockMvc.perform(post(BASE_PATH + REGISTER_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(ID.intValue())))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$.role", is(ROLE.toString())));
	}
	
	@Test
	public void GetUserDetails_NoIssues_ReturnUserDetails() throws Exception {
		Principal principal = Mockito.mock(Principal.class);
		
		User mockUser = new User();
		mockUser.setId(ID);
		mockUser.setUsername(USERNAME);
		mockUser.setPassword(PASSWORD);
		mockUser.setRole(ROLE);
		
		when(principal.getName()).thenReturn(USERNAME);
		when(userService.getUser(ArgumentMatchers.any(Principal.class))).thenReturn(mockUser);

		mockMvc.perform(get(BASE_PATH + GET_ME_PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(ID.intValue())))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$.role", is(ROLE.toString())));
	}
	
	private String convertToJson(UserRequest postUser) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    return ow.writeValueAsString(postUser);
	}
}