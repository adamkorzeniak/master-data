package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.programming.model.IntellijShortcut;
import com.adamkorzeniak.masterdata.features.programming.repository.IntellijShortcutRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IntellijShortcutServiceImpl implements IntellijShortcutService {

    private final IntellijShortcutRepository intellijShortcutRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public IntellijShortcutServiceImpl(IntellijShortcutRepository intellijShortcutRepository,
                                       ApiQueryService apiQueryService) {
        this.intellijShortcutRepository = intellijShortcutRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<IntellijShortcut> searchIntellijShortcuts(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<IntellijShortcut> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return intellijShortcutRepository.findAll(spec);
    }

    @Override
    public Optional<IntellijShortcut> findIntellijShortcutById(Long id) {
        return intellijShortcutRepository.findById(id);
    }

    @Override
    public IntellijShortcut addIntellijShortcut(IntellijShortcut intellijShortcut) {
        intellijShortcut.setId(-1L);
        return intellijShortcutRepository.save(intellijShortcut);
    }

    @Override
    public IntellijShortcut updateIntellijShortcut(Long id, IntellijShortcut intellijShortcut) {
        intellijShortcut.setId(id);
        return intellijShortcutRepository.save(intellijShortcut);
    }

    @Override
    public void deleteIntellijShortcut(Long id) {
        intellijShortcutRepository.deleteById(id);
    }

    @Override
    public boolean isIntellijShortcutExist(Long id) {
        return intellijShortcutRepository.existsById(id);
    }
}
