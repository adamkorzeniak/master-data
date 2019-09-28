package com.adamkorzeniak.masterdata.features.error.service;

import java.util.List;
import java.util.Map;

import com.adamkorzeniak.masterdata.features.error.model.Error;

public interface ErrorService {

    List<Error> searchErrors(Map<String, String> requestParams);

    Error addError(Error error);

    void deleteError(Long id);

    boolean isErrorExist(Long errorId);
}
