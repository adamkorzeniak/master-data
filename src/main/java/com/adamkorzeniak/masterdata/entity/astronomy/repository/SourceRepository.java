package com.adamkorzeniak.masterdata.entity.astronomy.repository;

import com.adamkorzeniak.masterdata.entity.astronomy.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long>, JpaSpecificationExecutor<Source> {
}
