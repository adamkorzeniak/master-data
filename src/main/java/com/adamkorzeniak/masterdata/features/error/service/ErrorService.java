package com.adamkorzeniak.masterdata.features.error.service;

import com.adamkorzeniak.masterdata.features.error.model.Error;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ErrorService {

    List<Error> searchErrors(Map<String, String> allRequestParams);

    Optional<Error> findErrorById(Long id);

    Error addError(Error error);

    Error updateError(Long id, Error error);

    void deleteError(Long id);

    boolean isErrorExist(Long id);
}
