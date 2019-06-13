package com.adamkorzeniak.masterdata.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.adamkorzeniak.masterdata.features.account.model.User;
import com.adamkorzeniak.masterdata.features.account.model.UserDTO;
import com.adamkorzeniak.masterdata.features.account.service.UserService;
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

	private String basePath = "/v0";

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void AddUser_NoIssues_ReturnsAddedUser() throws Exception {
		UserDTO postUser = new UserDTO();
		postUser.setUsername("adam");
		postUser.setPassword("password");
		User mockUser = new User();
		mockUser.setUsername("adam");
		mockUser.setPassword("password");
		
	    String requestJson = convertToJson(postUser);

		doReturn(mockUser).when(userService).register(ArgumentMatchers.any());

		mockMvc.perform(post(basePath + "/register").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username", is("adam")))
				.andExpect(jsonPath("$.password", nullValue()));
	}
	
	private String convertToJson(UserDTO postUser) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    return ow.writeValueAsString(postUser);
	}
}