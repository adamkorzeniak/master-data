package com.adamkorzeniak.masterdata.features.diet.repository;

import com.adamkorzeniak.masterdata.features.diet.model.RecipeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long>, JpaSpecificationExecutor<RecipeTag> {
}
