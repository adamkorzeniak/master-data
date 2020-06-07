package com.adamkorzeniak.masterdata.features.management.repository;

import com.adamkorzeniak.masterdata.features.management.model.RoutineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineItemRepository extends JpaRepository<RoutineItem, Long>, JpaSpecificationExecutor<RoutineItem> {
}
