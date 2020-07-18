package com.adamkorzeniak.masterdata.api.basic.controller;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface GenericController {

    ResponseEntity<List<Map<String, Object>>> searchAll(Map<String, String> allRequestParams, Class<? extends DatabaseEntity> resourceClass);

    ResponseEntity<Object> findById(Long id, Class<? extends DatabaseEntity> resourceClass);

    ResponseEntity<Object> add(DatabaseEntity entity, Class<? extends DatabaseEntity> resourceClass);

    ResponseEntity<Object> update(Long id, DatabaseEntity entity, Class<? extends DatabaseEntity> resourceClass);

    ResponseEntity<Void> delete(Long id, Class<? extends DatabaseEntity> resourceClass);
}
