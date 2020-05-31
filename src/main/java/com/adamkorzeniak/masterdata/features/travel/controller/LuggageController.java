package com.adamkorzeniak.masterdata.features.travel.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.travel.model.Luggage;
import com.adamkorzeniak.masterdata.features.travel.service.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Travel")
public class LuggageController {

    private static final String LUGGAGE_RESOURCE_NAME = "Luggage";

    private final LuggageService luggageService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public LuggageController(LuggageService luggageService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.luggageService = luggageService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of people with 200 OK.
     * <p>
     * If there are no people it returns empty list with 204 No Content
     */
    @GetMapping("/luggage")
    public ResponseEntity<List<Map<String, Object>>> findLuggages(@RequestParam Map<String, String> allRequestParams) {
        List<Luggage> people = luggageService.searchLuggages(allRequestParams);
        if (people.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(people, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns luggage with given id with 200 OK.
     * <p>
     * If luggage with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/luggage/{luggageId}")
    public ResponseEntity<Luggage> findLuggageById(@PathVariable("luggageId") Long luggageId) {
        Optional<Luggage> luggage = luggageService.findLuggageById(luggageId);
        if (luggage.isEmpty()) {
            throw new NotFoundException(LUGGAGE_RESOURCE_NAME, luggageId);
        }
        return new ResponseEntity<>(luggage.get(), HttpStatus.OK);
    }

    /**
     * Creates a luggage in database.
     * Returns created luggage with 201 Created.
     * <p>
     * If provided luggage data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/luggage")
    public ResponseEntity<Luggage> addLuggage(@RequestBody @Valid Luggage luggage) {
        Luggage newLuggage = luggageService.addLuggage(luggage);
        return new ResponseEntity<>(newLuggage, HttpStatus.CREATED);
    }

    /**
     * Updates a luggage with given id in database. Returns updated luggage with 200 OK.
     * <p>
     * If luggage with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided luggage data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/luggage/{luggageId}")
    public ResponseEntity<Luggage> updateLuggage(@RequestBody @Valid Luggage luggage, @PathVariable Long luggageId) {
        boolean exists = luggageService.isLuggageExist(luggageId);
        if (!exists) {
            throw new NotFoundException(LUGGAGE_RESOURCE_NAME, luggageId);
        }
        Luggage newLuggage = luggageService.updateLuggage(luggageId, luggage);
        return new ResponseEntity<>(newLuggage, HttpStatus.OK);
    }

    /**
     * Deletes a luggage with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If luggage with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/luggage/{luggageId}")
    public ResponseEntity<Void> deleteLuggage(@PathVariable Long luggageId) {
        boolean exists = luggageService.isLuggageExist(luggageId);
        if (!exists) {
            throw new NotFoundException(LUGGAGE_RESOURCE_NAME, luggageId);
        }
        luggageService.deleteLuggage(luggageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
