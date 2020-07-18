package com.adamkorzeniak.masterdata.entity.travel.repository;

import com.adamkorzeniak.masterdata.entity.travel.model.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LuggageRepository extends JpaRepository<Luggage, Long>, JpaSpecificationExecutor<Luggage> {
}
