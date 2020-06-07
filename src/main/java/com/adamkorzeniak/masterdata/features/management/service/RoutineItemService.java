package com.adamkorzeniak.masterdata.features.management.service;

import com.adamkorzeniak.masterdata.features.management.model.RoutineItem;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoutineItemService {

    List<RoutineItem> searchRoutineItems(Map<String, String> allRequestParams);

    Optional<RoutineItem> findRoutineItemById(Long id);

    RoutineItem addRoutineItem(RoutineItem routineItem);

    RoutineItem updateRoutineItem(Long id, RoutineItem routineItem);

    void deleteRoutineItem(Long id);

    boolean isRoutineItemExist(Long id);
}
