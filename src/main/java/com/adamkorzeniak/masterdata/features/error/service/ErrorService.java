package com.adamkorzeniak.masterdata.features.error.service;

import com.adamkorzeniak.masterdata.features.error.model.Error;

import java.util.List;
import java.util.Map;

public interface ErrorService {

    List<Error> searchErrors(Map<String, String> requestParams);

    Error addError(Error error);

    void deleteError(Long id);

    boolean isErrorExist(Long errorId);
}
