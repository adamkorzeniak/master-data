package com.adamkorzeniak.masterdata.features.travel.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.travel.model.Luggage;
import com.adamkorzeniak.masterdata.features.travel.repository.LuggageRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LuggageServiceImpl implements LuggageService {

    private final LuggageRepository luggageRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public LuggageServiceImpl(LuggageRepository luggageRepository,
                             ApiQueryService apiQueryService) {
        this.luggageRepository = luggageRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Luggage> searchLuggages(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Luggage> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return luggageRepository.findAll(spec);
    }

    @Override
    public Optional<Luggage> findLuggageById(Long id) {
        return luggageRepository.findById(id);
    }

    @Override
    public Luggage addLuggage(Luggage luggage) {
        luggage.setId(-1L);
        return luggageRepository.save(luggage);
    }

    @Override
    public Luggage updateLuggage(Long id, Luggage luggage) {
        luggage.setId(id);
        return luggageRepository.save(luggage);
    }

    @Override
    public void deleteLuggage(Long id) {
        luggageRepository.deleteById(id);
    }

    @Override
    public boolean isLuggageExist(Long id) {
        return luggageRepository.existsById(id);
    }
}
