package com.adamkorzeniak.masterdata.entity.programming.repository;

import com.adamkorzeniak.masterdata.entity.programming.model.IntellijShortcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IntellijShortcutRepository extends JpaRepository<IntellijShortcut, Long>, JpaSpecificationExecutor<IntellijShortcut> {
}