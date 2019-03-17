package com.adamkorzeniak.masterdata.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.error.model.Error;
import com.adamkorzeniak.masterdata.error.repository.ErrorRepository;
import com.adamkorzeniak.masterdata.error.service.ErrorService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class ErrorServiceTest {

	@MockBean
	private ErrorRepository errorRepository;

	@Autowired
	private ErrorService errorService;

	@Test
	public void SearchErrors_NoIssues_ReturnsListOfErrors() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("search-name", "client");
		Error error1 = new Error();
		error1.setName("Client error");
		Error error2 = new Error();
		error2.setName("Client failure");
		List<Error> errors = Arrays.asList(error1, error2);

		when(errorRepository.findAll(Matchers.<Specification>any())).thenReturn(errors);

		List<Error> result = errorService.searchErrors(params);
		assertThat(result).isNotNull();
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getName()).isEqualTo("Client error");
		assertThat(result.get(1).getName()).isEqualTo("Client failure");

	}

	@Test
	public void AddError_ErrorValid_ReturnsCreatedError() throws Exception {
		Long id = 1L;
		Error error = new Error();
		error.setId(id);
		error.setName("ClientError");

		when(errorRepository.save(Matchers.<Error>any())).thenAnswer(
			mockRepositorySaveInvocation(error)
		);

		Error result = errorService.addError(error);
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("ClientError");
		assertThat(result.getId()).isEqualTo(100L);
		
	}
	
	@Test
	public void DeleteError_ErrorIdValid_DeletesError() throws Exception {
		Long id = 1L;
		errorService.deleteError(id);
	}

	@Test
	public void IsErrorExist_ErrorValid_ReturnsTrue() throws Exception {
		Long id = 1L;
		when(errorRepository.existsById(id)).thenReturn(true);

		assertThat(errorService.isErrorExist(id)).isTrue();
	}
	
	private Answer<?> mockRepositorySaveInvocation(Error error) {
		return invocation -> {
		    Object argument = invocation.getArguments()[0];
		    Error receivedError = (Error) argument;
		    if (receivedError.getId() >= 0 ) {
		        return error;
		    } else {
		    	Error newError = new Error();
		    	newError.setId(100L);
		    	newError.setName(error.getName());
		        return newError;
		    }
		};
	}
}
