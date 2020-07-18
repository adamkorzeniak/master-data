package com.adamkorzeniak.masterdata.entity.management.repository;

import com.adamkorzeniak.masterdata.entity.management.model.RoutineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineItemRepository extends JpaRepository<RoutineItem, Long>, JpaSpecificationExecutor<RoutineItem> {
}
