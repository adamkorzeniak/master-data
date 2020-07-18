package com.adamkorzeniak.masterdata.api.basic.controller;

import com.adamkorzeniak.masterdata.features.general.EntityWithId;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GenericController {

    ResponseEntity<List<Map<String, Object>>> searchAll(Map<String, String> allRequestParams, Class<? extends EntityWithId> responseClass);

    ResponseEntity<Object> findById(Long id, Class<? extends EntityWithId> responseClass);

    ResponseEntity<Object> add(EntityWithId entity, Class<? extends EntityWithId> resourceClass);

    ResponseEntity<Object> update(Long id, EntityWithId entity, Class<? extends EntityWithId> resourceClass);

    ResponseEntity<Void> delete(Long id, Class<? extends EntityWithId> resourceClass);
}
