package com.adamkorzeniak.masterdata.features.product.repository;

import com.adamkorzeniak.masterdata.features.product.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long>, JpaSpecificationExecutor<ProductTag> {
}
