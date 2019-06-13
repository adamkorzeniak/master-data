package com.adamkorzeniak.masterdata.features.error.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.adamkorzeniak.masterdata.features.error.model.Error;

@Repository
public interface ErrorRepository extends JpaRepository<Error, Long>, JpaSpecificationExecutor<Error> {
}
