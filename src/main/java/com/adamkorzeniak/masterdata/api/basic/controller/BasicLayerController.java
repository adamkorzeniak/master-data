package com.adamkorzeniak.masterdata.api.basic.controller;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import com.adamkorzeniak.masterdata.api.common.providers.ResourceClassProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/basic/{feature}/{resource}")
public class BasicLayerController {

    private final GenericController genericController;
    private final ResourceClassProvider resourceClassProvider;
    private final ObjectMapper objectMapper;

    @Autowired
    public BasicLayerController(GenericControllerImpl genericService, ResourceClassProvider resourceClassProvider, ObjectMapper objectMapper) {
        this.genericController = genericService;
        this.resourceClassProvider = resourceClassProvider;
        this.objectMapper = objectMapper;
    }

    /**
     * Returns list of objects for given feature and resource with 200 OK.
     * <p>
     * If there are no objects for given feature and resource it returns empty list with 204 No Content
     * <p>
     * If there is no combination for given feature and resource found it returns 404 not Found
     */
    @GetMapping("")
    public ResponseEntity<List<Map<String, Object>>> searchAll(
            @PathVariable("feature") String feature,
            @PathVariable("resource") String resource,
            @RequestParam Map<String, String> allRequestParams
    ) {
        Class<? extends DatabaseEntity> resourceClass = resourceClassProvider.getClass(feature, resource);
        return genericController.searchAll(allRequestParams, resourceClass);
    }

    /**
     * Returns object for given feature and resource with given id with 200 OK.
     * <p>
     * If object with given id for given feature and resource does not exist it returns error response with 404 Not Found
     * <p>
     * If there is no combination for given feature and resource found it returns 404 not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(
            @PathVariable("feature") String feature,
            @PathVariable("resource") String resource,
            @PathVariable("id") Long id) {
        Class<? extends DatabaseEntity> resourceClass = resourceClassProvider.getClass(feature, resource);
        return genericController.findById(id, resourceClass);
    }

    /**
     * Creates an object for given feature and resource.
     * Returns created object with 201 Created.
     * <p>
     * If provided object data is invalid for given feature and resource it returns 400 Bad Request.
     * <p>
     * If there is no combination for given feature and resource found it returns 404 not Found
     */
    @PostMapping("")
    public ResponseEntity<Object> add(
            @PathVariable("feature") String feature,
            @PathVariable("resource") String resource,
            @RequestBody @Valid Object object) {
        Class<? extends DatabaseEntity> resourceClass = resourceClassProvider.getClass(feature, resource);
        DatabaseEntity entity = objectMapper.convertValue(object, resourceClass);
        return genericController.add(entity, resourceClass);
    }

    /**
     * Updates an object with given id for given feature and resource.
     * Returns updated object with 200 OK.
     * <p>
     * If object with given id for given feature and resource does not exist it returns error response with 404 Not Found
     * <p>
     * If there is no combination for given feature and resource found it returns 404 not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("feature") String feature,
            @PathVariable("resource") String resource,
            @PathVariable Long id,
            @RequestBody @Valid Object object) {
        Class<? extends DatabaseEntity> resourceClass = resourceClassProvider.getClass(feature, resource);
        DatabaseEntity entity = objectMapper.convertValue(object, resourceClass);
        return genericController.update(id, entity, resourceClass);
    }

    /**
     * Deletes an object with given id for given feature and resource.
     * Returns empty response with 204 No Content.
     * <p>
     * If object with given id for given feature and resource does not exist it returns error response with 404 Not Found
     * <p>
     * If there is no combination for given feature and resource found it returns 404 not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("feature") String feature,
            @PathVariable("resource") String resource,
            @PathVariable Long id) {
        Class<? extends DatabaseEntity> resourceClass = resourceClassProvider.getClass(feature, resource);
        return genericController.delete(id, resourceClass);
    }
}
