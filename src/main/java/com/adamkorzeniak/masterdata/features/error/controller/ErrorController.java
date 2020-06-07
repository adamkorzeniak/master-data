package com.adamkorzeniak.masterdata.features.error.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.error.model.Error;
import com.adamkorzeniak.masterdata.features.error.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Error")
public class ErrorController {

    private static final String ERROR_RESOURCE_NAME = "Error";

    private final ErrorService errorService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ErrorController(ErrorService errorService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.errorService = errorService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of errors with 200 OK.
     * <p>
     * If there are no errors it returns empty list with 204 No Content
     */
    @GetMapping("/errors")
    public ResponseEntity<List<Map<String, Object>>> findErrors(@RequestParam Map<String, String> allRequestParams) {
        List<Error> errors = errorService.searchErrors(allRequestParams);
        if (errors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(errors, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns error with given id with 200 OK.
     * <p>
     * If error with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/errors/{errorId}")
    public ResponseEntity<Error> findErrorById(@PathVariable("errorId") Long errorId) {
        Optional<Error> error = errorService.findErrorById(errorId);
        if (error.isEmpty()) {
            throw new NotFoundException(ERROR_RESOURCE_NAME, errorId);
        }
        return new ResponseEntity<>(error.get(), HttpStatus.OK);
    }

    /**
     * Creates a error in database.
     * Returns created error with 201 Created.
     * <p>
     * If provided error data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/errors")
    public ResponseEntity<Error> addError(@RequestBody @Valid Error error) {
        Error newError = errorService.addError(error);
        return new ResponseEntity<>(newError, HttpStatus.CREATED);
    }

    /**
     * Updates a error with given id in database. Returns updated error with 200 OK.
     * <p>
     * If error with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided error data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/errors/{errorId}")
    public ResponseEntity<Error> updateError(@RequestBody @Valid Error error, @PathVariable Long errorId) {
        boolean exists = errorService.isErrorExist(errorId);
        if (!exists) {
            throw new NotFoundException(ERROR_RESOURCE_NAME, errorId);
        }
        Error newError = errorService.updateError(errorId, error);
        return new ResponseEntity<>(newError, HttpStatus.OK);
    }

    /**
     * Deletes a error with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If error with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/errors/{errorId}")
    public ResponseEntity<Void> deleteError(@PathVariable Long errorId) {
        boolean exists = errorService.isErrorExist(errorId);
        if (!exists) {
            throw new NotFoundException(ERROR_RESOURCE_NAME, errorId);
        }
        errorService.deleteError(errorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
