package com.adamkorzeniak.masterdata.features.management.repository;

import com.adamkorzeniak.masterdata.features.management.model.LearningTechnique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningTechniqueRepository extends JpaRepository<LearningTechnique, Long>, JpaSpecificationExecutor<LearningTechnique> {
}
