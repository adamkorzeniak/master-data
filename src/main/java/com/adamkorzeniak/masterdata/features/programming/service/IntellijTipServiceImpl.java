package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.programming.model.IntellijTip;
import com.adamkorzeniak.masterdata.features.programming.repository.IntellijTipRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IntellijTipServiceImpl implements IntellijTipService {

    private final IntellijTipRepository intellijTipRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public IntellijTipServiceImpl(IntellijTipRepository intellijTipRepository,
                                  ApiQueryService apiQueryService) {
        this.intellijTipRepository = intellijTipRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<IntellijTip> searchIntellijTips(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<IntellijTip> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return intellijTipRepository.findAll(spec);
    }

    @Override
    public Optional<IntellijTip> findIntellijTipById(Long id) {
        return intellijTipRepository.findById(id);
    }

    @Override
    public IntellijTip addIntellijTip(IntellijTip intellijTip) {
        intellijTip.setId(-1L);
        return intellijTipRepository.save(intellijTip);
    }

    @Override
    public IntellijTip updateIntellijTip(Long id, IntellijTip intellijTip) {
        intellijTip.setId(id);
        return intellijTipRepository.save(intellijTip);
    }

    @Override
    public void deleteIntellijTip(Long id) {
        intellijTipRepository.deleteById(id);
    }

    @Override
    public boolean isIntellijTipExist(Long id) {
        return intellijTipRepository.existsById(id);
    }
}
