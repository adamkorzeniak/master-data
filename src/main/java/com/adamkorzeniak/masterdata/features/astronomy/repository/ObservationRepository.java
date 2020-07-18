package com.adamkorzeniak.masterdata.features.astronomy.repository;

import com.adamkorzeniak.masterdata.features.astronomy.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long>, JpaSpecificationExecutor<Observation> {
}
