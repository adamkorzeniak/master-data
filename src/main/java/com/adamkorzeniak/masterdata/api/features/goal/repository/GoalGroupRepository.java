package com.adamkorzeniak.masterdata.api.features.goal.repository;

import com.adamkorzeniak.masterdata.api.features.goal.model.entity.GoalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalGroupRepository extends JpaRepository<GoalGroup, Long> {

    List<GoalGroup> findAll();
}
