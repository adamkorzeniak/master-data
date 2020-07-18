package com.adamkorzeniak.masterdata.api.basic.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.basic.service.GenericService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.basic.api.EntityWithId;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GenericControllerImpl implements GenericController {

    private final GenericService genericService;
    private final ApiQueryService apiQueryService;
    private final ApiResponseService apiResponseService;

    public GenericControllerImpl(GenericService genericService, ApiQueryService apiQueryService, ApiResponseService apiResponseService) {
        this.genericService = genericService;
        this.apiQueryService = apiQueryService;
        this.apiResponseService = apiResponseService;
    }

    public ResponseEntity<List<Map<String, Object>>> searchAll(Map<String, String> allRequestParams, Class<? extends EntityWithId> responseClass) {
        List<Object> response = genericService.searchAll(allRequestParams, responseClass);
        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(response, selectExpression), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, Class<? extends EntityWithId> responseClass) {
        Optional<Object> response = genericService.findById(id, responseClass);
        if (response.isEmpty()) {
            throw new NotFoundException(responseClass.toString(), id);
        }
        return new ResponseEntity<>(response.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> add(EntityWithId entity, Class<? extends EntityWithId> resourceClass) {
        Object newObject = genericService.add(entity, resourceClass);
        return new ResponseEntity<>(newObject, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> update(Long id, EntityWithId entity, Class<? extends EntityWithId> resourceClass) {
        boolean exists = genericService.exists(id, resourceClass);
        if (!exists) {
            throw new NotFoundException(resourceClass, id);
        }
        Object newObject = genericService.update(id, entity, resourceClass);
        return new ResponseEntity<>(newObject, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id, Class<? extends EntityWithId> resourceClass) {
        boolean exists = genericService.exists(id, resourceClass);
        if (!exists) {
            throw new NotFoundException(resourceClass, id);
        }
        genericService.delete(id, resourceClass);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
