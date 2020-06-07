package com.adamkorzeniak.masterdata.features.management.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.management.model.LearningTechnique;
import com.adamkorzeniak.masterdata.features.management.repository.LearningTechniqueRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LearningTechniqueServiceImpl implements LearningTechniqueService {

    private final LearningTechniqueRepository learningTechniqueRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public LearningTechniqueServiceImpl(LearningTechniqueRepository learningTechniqueRepository,
                                        ApiQueryService apiQueryService) {
        this.learningTechniqueRepository = learningTechniqueRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<LearningTechnique> searchLearningTechniques(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<LearningTechnique> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return learningTechniqueRepository.findAll(spec);
    }

    @Override
    public Optional<LearningTechnique> findLearningTechniqueById(Long id) {
        return learningTechniqueRepository.findById(id);
    }

    @Override
    public LearningTechnique addLearningTechnique(LearningTechnique learningTechnique) {
        learningTechnique.setId(-1L);
        return learningTechniqueRepository.save(learningTechnique);
    }

    @Override
    public LearningTechnique updateLearningTechnique(Long id, LearningTechnique learningTechnique) {
        learningTechnique.setId(id);
        return learningTechniqueRepository.save(learningTechnique);
    }

    @Override
    public void deleteLearningTechnique(Long id) {
        learningTechniqueRepository.deleteById(id);
    }

    @Override
    public boolean isLearningTechniqueExist(Long id) {
        return learningTechniqueRepository.existsById(id);
    }
}
