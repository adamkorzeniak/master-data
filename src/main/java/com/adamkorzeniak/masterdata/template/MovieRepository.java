package com.adamkorzeniak.masterdata.template;

import com.adamkorzeniak.masterdata.features.qwertyP.model.Qwerty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QwertyRepository extends JpaRepository<Qwerty, Long>, JpaSpecificationExecutor<Qwerty> {
}
