package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import com.adamkorzeniak.masterdata.features.programming.model.ChecklistGroup;
import com.adamkorzeniak.masterdata.features.programming.repository.ChecklistGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChecklistGroupServiceImpl implements ChecklistGroupService {

    private final ChecklistGroupRepository checklistGroupRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ChecklistGroupServiceImpl(ChecklistGroupRepository checklistGroupRepository,
                            ApiQueryService apiQueryService) {
        this.checklistGroupRepository = checklistGroupRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<ChecklistGroup> searchChecklistGroups(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<ChecklistGroup> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return checklistGroupRepository.findAll(spec);
    }

    @Override
    public Optional<ChecklistGroup> findChecklistGroupById(Long id) {
        return checklistGroupRepository.findById(id);
    }

    @Override
    public ChecklistGroup addChecklistGroup(ChecklistGroup checklistGroup) {
        checklistGroup.setId(-1L);
        return checklistGroupRepository.save(checklistGroup);
    }

    @Override
    public ChecklistGroup updateChecklistGroup(Long id, ChecklistGroup checklistGroup) {
        checklistGroup.setId(id);
        return checklistGroupRepository.save(checklistGroup);
    }

    @Override
    public void deleteChecklistGroup(Long id) {
        checklistGroupRepository.deleteById(id);
    }

    @Override
    public boolean isChecklistGroupExist(Long id) {
        return checklistGroupRepository.existsById(id);
    }
}
