package com.adamkorzeniak.masterdata.api.basic.service;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericService {

    List<Object> searchAll(Map<String, String> allRequestParams, Class<? extends DatabaseEntity> responseClass);

    Optional<Object> findById(Long id, Class<? extends DatabaseEntity> responseClass);

    Object add(DatabaseEntity entity, Class<? extends DatabaseEntity> resourceClass);

    boolean exists(Long id, Class<? extends DatabaseEntity> resourceClass);

    Object update(Long id, DatabaseEntity entity, Class<? extends DatabaseEntity> resourceClass);

    void delete(Long id, Class<? extends DatabaseEntity> resourceClass);
}
