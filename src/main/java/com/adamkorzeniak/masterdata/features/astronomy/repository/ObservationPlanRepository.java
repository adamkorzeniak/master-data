package com.adamkorzeniak.masterdata.features.astronomy.repository;

import com.adamkorzeniak.masterdata.features.astronomy.model.ObservationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationPlanRepository extends JpaRepository<ObservationPlan, Long>, JpaSpecificationExecutor<ObservationPlan> {
}
