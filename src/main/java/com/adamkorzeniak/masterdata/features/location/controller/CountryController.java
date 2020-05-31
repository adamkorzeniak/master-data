package com.adamkorzeniak.masterdata.features.location.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.location.model.Country;
import com.adamkorzeniak.masterdata.features.location.service.CountryService;
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
public class CountryController {

    private static final String COUNTRY_RESOURCE_NAME = "Country";

    private final CountryService countryService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public CountryController(CountryService countryService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.countryService = countryService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of countries with 200 OK.
     * <p>
     * If there are no countries it returns empty list with 204 No Content
     */
    @GetMapping("/countries")
    public ResponseEntity<List<Map<String, Object>>> findCountrys(@RequestParam Map<String, String> allRequestParams) {
        List<Country> countries = countryService.searchCountrys(allRequestParams);
        if (countries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(countries, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns country with given id with 200 OK.
     * <p>
     * If country with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/countries/{countryId}")
    public ResponseEntity<Country> findCountryById(@PathVariable("countryId") Long countryId) {
        Optional<Country> country = countryService.findCountryById(countryId);
        if (country.isEmpty()) {
            throw new NotFoundException(COUNTRY_RESOURCE_NAME, countryId);
        }
        return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    /**
     * Creates a country in database.
     * Returns created country with 201 Created.
     * <p>
     * If provided country data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/countries")
    public ResponseEntity<Country> addCountry(@RequestBody @Valid Country country) {
        Country newCountry = countryService.addCountry(country);
        return new ResponseEntity<>(newCountry, HttpStatus.CREATED);
    }

    /**
     * Updates a country with given id in database. Returns updated country with 200 OK.
     * <p>
     * If country with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided country data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/countries/{countryId}")
    public ResponseEntity<Country> updateCountry(@RequestBody @Valid Country country, @PathVariable Long countryId) {
        boolean exists = countryService.isCountryExist(countryId);
        if (!exists) {
            throw new NotFoundException(COUNTRY_RESOURCE_NAME, countryId);
        }
        Country newCountry = countryService.updateCountry(countryId, country);
        return new ResponseEntity<>(newCountry, HttpStatus.OK);
    }

    /**
     * Deletes a country with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If country with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/countries/{countryId}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long countryId) {
        boolean exists = countryService.isCountryExist(countryId);
        if (!exists) {
            throw new NotFoundException(COUNTRY_RESOURCE_NAME, countryId);
        }
        countryService.deleteCountry(countryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
