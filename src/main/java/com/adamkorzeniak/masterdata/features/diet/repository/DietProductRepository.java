package com.adamkorzeniak.masterdata.features.diet.repository;

import com.adamkorzeniak.masterdata.features.diet.model.DietProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DietProductRepository extends JpaRepository<DietProduct, Long>, JpaSpecificationExecutor<DietProduct> {
}
