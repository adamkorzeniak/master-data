package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.features.programming.model.IntellijShortcut;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IntellijShortcutService {

    List<IntellijShortcut> searchIntellijShortcuts(Map<String, String> allRequestParams);

    Optional<IntellijShortcut> findIntellijShortcutById(Long id);

    IntellijShortcut addIntellijShortcut(IntellijShortcut intellijShortcut);

    IntellijShortcut updateIntellijShortcut(Long id, IntellijShortcut intellijShortcut);

    void deleteIntellijShortcut(Long id);

    boolean isIntellijShortcutExist(Long id);
}
