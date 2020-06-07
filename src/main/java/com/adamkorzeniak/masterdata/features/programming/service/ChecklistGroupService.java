package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.features.programming.model.ChecklistGroup;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChecklistGroupService {

    List<ChecklistGroup> searchChecklistGroups(Map<String, String> allRequestParams);

    Optional<ChecklistGroup> findChecklistGroupById(Long id);

    ChecklistGroup addChecklistGroup(ChecklistGroup checklistGroup);

    ChecklistGroup updateChecklistGroup(Long id, ChecklistGroup checklistGroup);

    void deleteChecklistGroup(Long id);

    boolean isChecklistGroupExist(Long id);
}
