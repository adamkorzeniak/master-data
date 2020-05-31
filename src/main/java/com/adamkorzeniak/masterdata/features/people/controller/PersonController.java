package com.adamkorzeniak.masterdata.features.people.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.people.model.Person;
import com.adamkorzeniak.masterdata.features.people.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/People")
public class PersonController {

    private static final String PERSON_RESOURCE_NAME = "Person";

    private final PersonService personService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public PersonController(PersonService personService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.personService = personService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of persons with 200 OK.
     * <p>
     * If there are no persons it returns empty list with 204 No Content
     */
    @GetMapping("/persons")
    public ResponseEntity<List<Map<String, Object>>> findPersons(@RequestParam Map<String, String> allRequestParams) {
        List<Person> persons = personService.searchPersons(allRequestParams);
        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(persons, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns person with given id with 200 OK.
     * <p>
     * If person with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/persons/{personId}")
    public ResponseEntity<Person> findPersonById(@PathVariable("personId") Long personId) {
        Optional<Person> person = personService.findPersonById(personId);
        if (person.isEmpty()) {
            throw new NotFoundException(PERSON_RESOURCE_NAME, personId);
        }
        return new ResponseEntity<>(person.get(), HttpStatus.OK);
    }

    /**
     * Creates a person in database.
     * Returns created person with 201 Created.
     * <p>
     * If provided person data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@RequestBody @Valid Person person) {
        Person newPerson = personService.addPerson(person);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    /**
     * Updates a person with given id in database. Returns updated person with 200 OK.
     * <p>
     * If person with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided person data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/persons/{personId}")
    public ResponseEntity<Person> updatePerson(@RequestBody @Valid Person person, @PathVariable Long personId) {
        boolean exists = personService.isPersonExist(personId);
        if (!exists) {
            throw new NotFoundException(PERSON_RESOURCE_NAME, personId);
        }
        Person newPerson = personService.updatePerson(personId, person);
        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }

    /**
     * Deletes a person with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If person with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/persons/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        boolean exists = personService.isPersonExist(personId);
        if (!exists) {
            throw new NotFoundException(PERSON_RESOURCE_NAME, personId);
        }
        personService.deletePerson(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
