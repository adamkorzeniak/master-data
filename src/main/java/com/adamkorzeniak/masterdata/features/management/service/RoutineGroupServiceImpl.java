package com.adamkorzeniak.masterdata.features.management.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.management.model.RoutineGroup;
import com.adamkorzeniak.masterdata.features.management.repository.RoutineGroupRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RoutineGroupServiceImpl implements RoutineGroupService {

    private final RoutineGroupRepository routineGroupRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RoutineGroupServiceImpl(RoutineGroupRepository routineGroupRepository,
                                     ApiQueryService apiQueryService) {
        this.routineGroupRepository = routineGroupRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<RoutineGroup> searchRoutineGroups(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<RoutineGroup> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return routineGroupRepository.findAll(spec);
    }

    @Override
    public Optional<RoutineGroup> findRoutineGroupById(Long id) {
        return routineGroupRepository.findById(id);
    }

    @Override
    public RoutineGroup addRoutineGroup(RoutineGroup routineGroup) {
        routineGroup.setId(-1L);
        return routineGroupRepository.save(routineGroup);
    }

    @Override
    public RoutineGroup updateRoutineGroup(Long id, RoutineGroup routineGroup) {
        routineGroup.setId(id);
        return routineGroupRepository.save(routineGroup);
    }

    @Override
    public void deleteRoutineGroup(Long id) {
        routineGroupRepository.deleteById(id);
    }

    @Override
    public boolean isRoutineGroupExist(Long id) {
        return routineGroupRepository.existsById(id);
    }
}
