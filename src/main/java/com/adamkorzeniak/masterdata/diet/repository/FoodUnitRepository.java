package com.adamkorzeniak.masterdata.diet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.diet.model.FoodUnit;

@Repository
public interface FoodUnitRepository extends JpaRepository<FoodUnit, Long>, JpaSpecificationExecutor<FoodUnit> {
}
