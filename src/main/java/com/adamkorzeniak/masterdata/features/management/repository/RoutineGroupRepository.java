package com.adamkorzeniak.masterdata.features.management.repository;

import com.adamkorzeniak.masterdata.features.management.model.RoutineGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineGroupRepository extends JpaRepository<RoutineGroup, Long>, JpaSpecificationExecutor<RoutineGroup> {
}
