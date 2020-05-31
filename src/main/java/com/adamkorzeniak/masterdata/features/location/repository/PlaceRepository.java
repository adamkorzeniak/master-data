package com.adamkorzeniak.masterdata.features.location.repository;

import com.adamkorzeniak.masterdata.features.location.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, JpaSpecificationExecutor<Place> {
}
