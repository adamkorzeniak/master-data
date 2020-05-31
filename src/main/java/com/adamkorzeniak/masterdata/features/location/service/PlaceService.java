package com.adamkorzeniak.masterdata.features.location.service;

import com.adamkorzeniak.masterdata.features.location.model.Place;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlaceService {

    List<Place> searchPlaces(Map<String, String> allRequestParams);

    Optional<Place> findPlaceById(Long id);

    Place addPlace(Place place);

    Place updatePlace(Long id, Place place);

    void deletePlace(Long id);

    boolean isPlaceExist(Long id);
}
