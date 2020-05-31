package com.adamkorzeniak.masterdata.features.location.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.location.model.Place;
import com.adamkorzeniak.masterdata.features.location.repository.PlaceRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository,
                            ApiQueryService apiQueryService) {
        this.placeRepository = placeRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Place> searchPlaces(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Place> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return placeRepository.findAll(spec);
    }

    @Override
    public Optional<Place> findPlaceById(Long id) {
        return placeRepository.findById(id);
    }

    @Override
    public Place addPlace(Place place) {
        place.setId(-1L);
        return placeRepository.save(place);
    }

    @Override
    public Place updatePlace(Long id, Place place) {
        place.setId(id);
        return placeRepository.save(place);
    }

    @Override
    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }

    @Override
    public boolean isPlaceExist(Long id) {
        return placeRepository.existsById(id);
    }
}
