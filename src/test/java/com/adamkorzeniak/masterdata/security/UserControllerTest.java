package com.adamkorzeniak.masterdata.security;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.adamkorzeniak.masterdata.security.model.User;
import com.adamkorzeniak.masterdata.security.model.UserDTO;
import com.adamkorzeniak.masterdata.security.service.UserService;
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
	public void GetUserById_CorrectIdProvided_ReturnsUser() throws Exception {
		Long id = 15L;
		User mocked = new User();
		mocked.setName("User");
		mocked.setId(15L);
		Optional<User> optional = Optional.of(mocked);

		doReturn(optional).when(userService).findUserById(id);

		mockMvc.perform(get(basePath + '/' + id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("User")))
				.andExpect(jsonPath("$.id", is(15)));
	}

	@Test
	public void AddUser_NoIssues_ReturnsAddedUser() throws Exception {
		UserDTO postUser = new UserDTO();
		postUser.setName("Comedy");
		User mockUser = new User();
		mockUser.setName("Comedy");
		
	    String requestJson = convertToJson(postUser);

		doReturn(mockUser).when(userService).addUser(Matchers.any());

		mockMvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("Comedy")));
	}
	
	@Test
	public void AddUser_MissingRequiredField_ReturnsBadRequestError() throws Exception {
		UserDTO postUser = new UserDTO();
		postUser.setId(100L);
		
	    String requestJson = convertToJson(postUser);
	    String errorMessage = "Invalid 'name' field value: null. Field 'name' must not be blank.";
	    
		mockMvc.perform(post(basePath)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ400")))
				.andExpect(jsonPath("$.title", is("Bad Request")))
				.andExpect(jsonPath("$.message", is(errorMessage)));
	}
	
	@Test
	public void UpdateUser_NoIssues_ReturnsUpdatedUser() throws Exception {
		Long id = 11L;
		UserDTO user = new UserDTO();
		user.setId(id);
		user.setName("Comedy");
		User mockUser = new User();
		mockUser.setId(id);
		mockUser.setName("Comedy");
		
	    String requestJson = convertToJson(user);

		doReturn(true).when(userService).isUserExist(id);
		doReturn(mockUser).when(userService).updateUser(Matchers.anyLong(), Matchers.any());

		mockMvc.perform(put(basePath + "/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(11)))
				.andExpect(jsonPath("$.name", is("Comedy")));
	}
	
	@Test
	public void UpdateUser_WrongIdProvided_ReturnsNotFoundError() throws Exception {
		Long id = 11L;
		UserDTO user = new UserDTO();
		user.setId(id);
		user.setName("Comedy");
	    String requestJson = convertToJson(user);

		doReturn(false).when(userService).isUserExist(id);
	    
		mockMvc.perform(put(basePath + "/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ404")))
				.andExpect(jsonPath("$.title", is("Not Found")))
				.andExpect(jsonPath("$.message", is("User not found: id=" + id)));
	}

	@Test
	public void DeleteUser_CorrectIdProvided_DeletedUser() throws Exception {
		Long id = 15L;

		doReturn(true).when(userService).isUserExist(id);

		mockMvc.perform(delete(basePath + "/" + id)).andExpect(status().isNoContent());
	}

	@Test
	public void DeleteUser_WrongIdProvided_ReturnsNotFoundError() throws Exception {
		Long id = 15L;

		doReturn(false).when(userService).isUserExist(id);

		mockMvc.perform(delete(basePath + "/" + id)).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ404")))
				.andExpect(jsonPath("$.title", is("Not Found")))
				.andExpect(jsonPath("$.message", is("User not found: id=" + id)));
	}
	
	@Test
	public void MergeUsers_NoIssues_ReturnsResultUser() throws Exception {
		Long oldId = 11L;
		Long newId = 22L;
		
		UserPatchDTO patchDTO = new UserPatchDTO();
		patchDTO.setOp("merge");
		patchDTO.setTargetUserId(newId);
		
		User targetUser = new User();
		targetUser.setId(newId);
		targetUser.setName("Comedy");
		
	    String requestJson = convertToJson(patchDTO);

		doReturn(true).when(userService).isUserExist(oldId);
		doReturn(true).when(userService).isUserExist(newId);
		doReturn(targetUser).when(userService).mergeUsers(Matchers.anyLong(), Matchers.any());

		mockMvc.perform(patch(basePath + "/" + oldId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(22)))
				.andExpect(jsonPath("$.name", is("Comedy")));
	}
	
	@Test
	public void MergeUsers_SourceUserNotExists_ReturnsErrorResult() throws Exception {
		Long oldId = 11L;
		Long newId = 22L;
		
		UserPatchDTO patchDTO = new UserPatchDTO();
		patchDTO.setOp("merge");
		patchDTO.setTargetUserId(newId);
		
	    String requestJson = convertToJson(patchDTO);

		doReturn(false).when(userService).isUserExist(oldId);
		doReturn(true).when(userService).isUserExist(newId);

		mockMvc.perform(patch(basePath + "/" + oldId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ404")))
				.andExpect(jsonPath("$.title", is("Not Found")))
				.andExpect(jsonPath("$.message", is("User not found: id=11")));
	}
	
	@Test
	public void MergeUsers_TargetUserNotExists_ReturnsErrorResult() throws Exception {
		Long oldId = 11L;
		Long newId = 22L;
		
		UserPatchDTO patchDTO = new UserPatchDTO();
		patchDTO.setOp("merge");
		patchDTO.setTargetUserId(newId);
		
	    String requestJson = convertToJson(patchDTO);

		doReturn(true).when(userService).isUserExist(oldId);
		doReturn(false).when(userService).isUserExist(newId);

		mockMvc.perform(patch(basePath + "/" + oldId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ404")))
				.andExpect(jsonPath("$.title", is("Not Found")))
				.andExpect(jsonPath("$.message", is("User not found: id=22")));
	}
	
	@Test
	public void PatchUser_OperationNotSupported_ReturnsErrorResult() throws Exception {
		Long newId = 22L;
		
		UserPatchDTO patchDTO = new UserPatchDTO();
		patchDTO.setOp("rename");
		patchDTO.setTargetUserId(newId);
		
	    String requestJson = convertToJson(patchDTO);

		mockMvc.perform(patch(basePath + "/" + 11)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.code", is("REQ400")))
				.andExpect(jsonPath("$.title", is("Bad Request")))
				.andExpect(jsonPath("$.message", is("Operation 'rename' is not supported for Patch method on User resource.")));
	}

	private String convertToJson(UserDTO postUser) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    return ow.writeValueAsString(postUser);
	}
}
