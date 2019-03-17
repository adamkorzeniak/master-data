package com.adamkorzeniak.masterdata.error.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.error.model.Error;
import com.adamkorzeniak.masterdata.error.model.ErrorDTO;
import com.adamkorzeniak.masterdata.error.service.ErrorService;
import com.adamkorzeniak.masterdata.error.service.ErrorServiceHelper;
import com.adamkorzeniak.masterdata.exception.NotFoundException;

@RestController
@RequestMapping("/v0/Error")
public class ErrorController {

	@Autowired
	private ErrorService errorService;

	/**
	 * Returns list of errors with 200 OK.
	 * <p>
	 * If there are no errors it returns empty list with 204 No Content
	 * 
	 * @return List of errors
	 */
	@GetMapping("/errors")
	public ResponseEntity<List<ErrorDTO>> findErrors(@RequestParam Map<String, String> allRequestParams) {

		List<ErrorDTO> dtos = errorService.searchErrors(allRequestParams).stream().map(ErrorServiceHelper::convertToDTO)
				.collect(Collectors.toList());

		if (dtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	/**
	 * Creates a error in database. Returns created error with 201 Created.
	 * <p>
	 * If provided error data is invalid it returns 400 Bad Request.
	 * 
	 * @param error - Error to be created
	 * @return Created error
	 */
	@PostMapping("/errors")
	public ResponseEntity<ErrorDTO> addError(@RequestBody @Valid ErrorDTO dto) {
		Error error = ErrorServiceHelper.convertToEntity(dto);
		Error newError = errorService.addError(error);
		return new ResponseEntity<>(ErrorServiceHelper.convertToDTO(newError), HttpStatus.CREATED);
	}

	/**
	 * Deletes a error with given id. Returns empty response with 204 No Content.
	 * <p>
	 * If error with given id does not exist it returns error response with 404 Not
	 * Found
	 * 
	 * @param errorId - Id of error
	 * @return Empty response
	 */
	@DeleteMapping("/errors/{errorId}")
	public ResponseEntity<Void> deleteError(@PathVariable Long errorId) {
		boolean exists = errorService.isErrorExist(errorId);
		if (!exists) {
			throw new NotFoundException("Error", errorId);
		}
		errorService.deleteError(errorId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
