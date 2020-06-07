package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.features.programming.model.ChecklistItem;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChecklistItemService {

    List<ChecklistItem> searchChecklistItems(Map<String, String> allRequestParams);

    Optional<ChecklistItem> findChecklistItemById(Long id);

    ChecklistItem addChecklistItem(ChecklistItem checklistItem);

    ChecklistItem updateChecklistItem(Long id, ChecklistItem checklistItem);

    void deleteChecklistItem(Long id);

    boolean isChecklistItemExist(Long id);
}
