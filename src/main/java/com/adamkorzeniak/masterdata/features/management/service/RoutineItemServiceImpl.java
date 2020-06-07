package com.adamkorzeniak.masterdata.features.management.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.management.model.RoutineItem;
import com.adamkorzeniak.masterdata.features.management.repository.RoutineItemRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoutineItemServiceImpl implements RoutineItemService {

    private final RoutineItemRepository routineItemRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RoutineItemServiceImpl(RoutineItemRepository routineItemRepository,
                                    ApiQueryService apiQueryService) {
        this.routineItemRepository = routineItemRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<RoutineItem> searchRoutineItems(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<RoutineItem> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return routineItemRepository.findAll(spec);
    }

    @Override
    public Optional<RoutineItem> findRoutineItemById(Long id) {
        return routineItemRepository.findById(id);
    }

    @Override
    public RoutineItem addRoutineItem(RoutineItem routineItem) {
        routineItem.setId(-1L);
        return routineItemRepository.save(routineItem);
    }

    @Override
    public RoutineItem updateRoutineItem(Long id, RoutineItem routineItem) {
        routineItem.setId(id);
        return routineItemRepository.save(routineItem);
    }

    @Override
    public void deleteRoutineItem(Long id) {
        routineItemRepository.deleteById(id);
    }

    @Override
    public boolean isRoutineItemExist(Long id) {
        return routineItemRepository.existsById(id);
    }
}
