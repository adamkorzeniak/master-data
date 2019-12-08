package com.adamkorzeniak.masterdata.features.error;

import com.adamkorzeniak.masterdata.features.error.model.Error;
import com.adamkorzeniak.masterdata.features.error.model.ErrorDTO;
import com.adamkorzeniak.masterdata.features.error.service.ErrorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
public class ErrorControllerTest {

    private static final String BASE_ERROR_PATH = "/v0/Error/errors";

    @MockBean
    private ErrorService errorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void SearchErrors_TwoErrorsMatching_TwoErrorsReturned() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("search-name", "client");

        Error error1 = new Error();
        error1.setId(15L);
        error1.setName("Client error");
        error1.setErrorId("master-data-web-111111");
        error1.setAppId("master-data-web");
        Error error2 = new Error();
        error2.setId(25L);
        error2.setName("Client failure");
        error2.setErrorId("master-data-web-222222");
        error2.setAppId("master-data-web");
        List<Error> errors = Arrays.asList(error1, error2);

        when(errorService.searchErrors(params)).thenReturn(errors);

        mockMvc.perform(get(BASE_ERROR_PATH).param("search-name", "client"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(15)))
                .andExpect(jsonPath("$[0].appId", is("master-data-web")))
                .andExpect(jsonPath("$[0].errorId", is("master-data-web-111111")))
                .andExpect(jsonPath("$[0].name", is("Client error")))
                .andExpect(jsonPath("$[1].id", is(25)))
                .andExpect(jsonPath("$[1].appId", is("master-data-web")))
                .andExpect(jsonPath("$[1].errorId", is("master-data-web-222222")))
                .andExpect(jsonPath("$[1].name", is("Client failure")));
    }

    @Test
    public void SearchErrors_NoErrorsMatching_NoErrorsReturned() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("search-name", "client");
        List<Error> errors = new ArrayList<>();

        when(errorService.searchErrors(params)).thenReturn(errors);

        mockMvc.perform(get(BASE_ERROR_PATH).param("search-name", "client"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void AddError_NoIssues_ReturnsAddedError() throws Exception {
        ErrorDTO postError = new ErrorDTO();
        postError.setName("Client error");
        postError.setErrorId("master-data-web-111111");
        postError.setAppId("master-data-web");
        Error mockError = new Error();
        mockError.setName("Client error");
        mockError.setErrorId("master-data-web-111111");
        mockError.setAppId("master-data-web");

        String requestJson = convertToJson(postError);

        when(errorService.addError(ArgumentMatchers.any())).thenReturn(mockError);

        mockMvc.perform(post(BASE_ERROR_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.appId", is("master-data-web")))
                .andExpect(jsonPath("$.errorId", is("master-data-web-111111")))
                .andExpect(jsonPath("$.name", is("Client error")));
    }

    @Test
    public void AddError_ValidationFailed_ReturnsBadRequestError() throws Exception {
        ErrorDTO postError = new ErrorDTO();
        postError.setId(100L);
        postError.setAppId("master-data-web");

        String requestJson = convertToJson(postError);
        String errorMessage = "Invalid 'errorId' field value: null. Field 'errorId' must not be empty.";

        mockMvc.perform(post(BASE_ERROR_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code", is("REQ000")))
                .andExpect(jsonPath("$.title", is("Bad Request")))
                .andExpect(jsonPath("$.message", is(errorMessage)));
    }

    @Test
    public void DeleteError_CorrectIdProvided_DeletedError() throws Exception {
        Long id = 15L;

        when(errorService.isErrorExist(id)).thenReturn(true);

        mockMvc.perform(delete(BASE_ERROR_PATH + "/" + id)).andExpect(status().isNoContent());
    }

    @Test
    public void DeleteError_WrongIdProvided_ReturnsNotFoundError() throws Exception {
        Long id = 15L;

        when(errorService.isErrorExist(id)).thenReturn(false);

        mockMvc.perform(delete(BASE_ERROR_PATH + "/" + id)).andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code", is("REQ001")))
                .andExpect(jsonPath("$.title", is("Not Found")))
                .andExpect(jsonPath("$.message", is("Error not found: id=" + id)));
    }

    private String convertToJson(ErrorDTO postError) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(postError);
    }

}
