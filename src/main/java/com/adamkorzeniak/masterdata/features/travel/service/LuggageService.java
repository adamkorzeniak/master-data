package com.adamkorzeniak.masterdata.features.travel.service;

import com.adamkorzeniak.masterdata.features.travel.model.Luggage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LuggageService {

    List<Luggage> searchLuggages(Map<String, String> allRequestParams);

    Optional<Luggage> findLuggageById(Long id);

    Luggage addLuggage(Luggage luggage);

    Luggage updateLuggage(Long id, Luggage luggage);

    void deleteLuggage(Long id);

    boolean isLuggageExist(Long id);
}
