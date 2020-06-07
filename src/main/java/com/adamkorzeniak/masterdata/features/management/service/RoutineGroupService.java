package com.adamkorzeniak.masterdata.features.management.service;

import com.adamkorzeniak.masterdata.features.management.model.RoutineGroup;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoutineGroupService {

    List<RoutineGroup> searchRoutineGroups(Map<String, String> allRequestParams);

    Optional<RoutineGroup> findRoutineGroupById(Long id);

    RoutineGroup addRoutineGroup(RoutineGroup routineGroup);

    RoutineGroup updateRoutineGroup(Long id, RoutineGroup routineGroup);

    void deleteRoutineGroup(Long id);

    boolean isRoutineGroupExist(Long id);
}
