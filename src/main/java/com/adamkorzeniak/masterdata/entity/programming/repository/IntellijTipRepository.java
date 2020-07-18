package com.adamkorzeniak.masterdata.entity.programming.repository;

import com.adamkorzeniak.masterdata.entity.programming.model.IntellijTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IntellijTipRepository extends JpaRepository<IntellijTip, Long>, JpaSpecificationExecutor<IntellijTip> {
}
