package com.adamkorzeniak.masterdata.features.management.service;

import com.adamkorzeniak.masterdata.features.management.model.LearningTechnique;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LearningTechniqueService {

    List<LearningTechnique> searchLearningTechniques(Map<String, String> allRequestParams);

    Optional<LearningTechnique> findLearningTechniqueById(Long id);

    LearningTechnique addLearningTechnique(LearningTechnique learningTechnique);

    LearningTechnique updateLearningTechnique(Long id, LearningTechnique learningTechnique);

    void deleteLearningTechnique(Long id);

    boolean isLearningTechniqueExist(Long id);
}
