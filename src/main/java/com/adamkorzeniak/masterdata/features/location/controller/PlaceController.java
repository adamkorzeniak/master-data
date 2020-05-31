package com.adamkorzeniak.masterdata.features.location.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.location.model.Place;
import com.adamkorzeniak.masterdata.features.location.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Location")
public class PlaceController {

    private static final String PLACE_RESOURCE_NAME = "Place";

    private final PlaceService placeService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public PlaceController(PlaceService placeService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.placeService = placeService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of places with 200 OK.
     * <p>
     * If there are no places it returns empty list with 204 No Content
     */
    @GetMapping("/places")
    public ResponseEntity<List<Map<String, Object>>> findPlaces(@RequestParam Map<String, String> allRequestParams) {
        List<Place> places = placeService.searchPlaces(allRequestParams);
        if (places.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(places, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns place with given id with 200 OK.
     * <p>
     * If place with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/places/{placeId}")
    public ResponseEntity<Place> findPlaceById(@PathVariable("placeId") Long placeId) {
        Optional<Place> place = placeService.findPlaceById(placeId);
        if (place.isEmpty()) {
            throw new NotFoundException(PLACE_RESOURCE_NAME, placeId);
        }
        return new ResponseEntity<>(place.get(), HttpStatus.OK);
    }

    /**
     * Creates a place in database.
     * Returns created place with 201 Created.
     * <p>
     * If provided place data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/places")
    public ResponseEntity<Place> addPlace(@RequestBody @Valid Place place) {
        Place newPlace = placeService.addPlace(place);
        return new ResponseEntity<>(newPlace, HttpStatus.CREATED);
    }

    /**
     * Updates a place with given id in database. Returns updated place with 200 OK.
     * <p>
     * If place with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided place data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/places/{placeId}")
    public ResponseEntity<Place> updatePlace(@RequestBody @Valid Place place, @PathVariable Long placeId) {
        boolean exists = placeService.isPlaceExist(placeId);
        if (!exists) {
            throw new NotFoundException(PLACE_RESOURCE_NAME, placeId);
        }
        Place newPlace = placeService.updatePlace(placeId, place);
        return new ResponseEntity<>(newPlace, HttpStatus.OK);
    }

    /**
     * Deletes a place with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If place with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long placeId) {
        boolean exists = placeService.isPlaceExist(placeId);
        if (!exists) {
            throw new NotFoundException(PLACE_RESOURCE_NAME, placeId);
        }
        placeService.deletePlace(placeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
