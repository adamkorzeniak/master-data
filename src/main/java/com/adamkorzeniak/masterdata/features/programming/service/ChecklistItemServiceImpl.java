package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.programming.model.ChecklistItem;
import com.adamkorzeniak.masterdata.features.programming.repository.ChecklistItemRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChecklistItemServiceImpl implements ChecklistItemService {

    private final ChecklistItemRepository checklistItemRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ChecklistItemServiceImpl(ChecklistItemRepository checklistItemRepository,
                                    ApiQueryService apiQueryService) {
        this.checklistItemRepository = checklistItemRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<ChecklistItem> searchChecklistItems(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<ChecklistItem> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return checklistItemRepository.findAll(spec);
    }

    @Override
    public Optional<ChecklistItem> findChecklistItemById(Long id) {
        return checklistItemRepository.findById(id);
    }

    @Override
    public ChecklistItem addChecklistItem(ChecklistItem checklistItem) {
        checklistItem.setId(-1L);
        return checklistItemRepository.save(checklistItem);
    }

    @Override
    public ChecklistItem updateChecklistItem(Long id, ChecklistItem checklistItem) {
        checklistItem.setId(id);
        return checklistItemRepository.save(checklistItem);
    }

    @Override
    public void deleteChecklistItem(Long id) {
        checklistItemRepository.deleteById(id);
    }

    @Override
    public boolean isChecklistItemExist(Long id) {
        return checklistItemRepository.existsById(id);
    }
}
