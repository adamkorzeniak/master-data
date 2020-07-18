package com.adamkorzeniak.masterdata.entity.personal.repository;

import com.adamkorzeniak.masterdata.entity.personal.model.DiaryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryItemRepository extends JpaRepository<DiaryItem, Long>, JpaSpecificationExecutor<DiaryItem> {
}
