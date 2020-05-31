package com.adamkorzeniak.masterdata.features.travel.repository;

import com.adamkorzeniak.masterdata.features.travel.model.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LuggageRepository extends JpaRepository<Luggage, Long>, JpaSpecificationExecutor<Luggage> {
}
