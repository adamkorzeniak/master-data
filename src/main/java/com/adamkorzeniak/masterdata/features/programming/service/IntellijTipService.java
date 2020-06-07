package com.adamkorzeniak.masterdata.features.programming.service;

import com.adamkorzeniak.masterdata.features.programming.model.IntellijTip;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IntellijTipService {

    List<IntellijTip> searchIntellijTips(Map<String, String> allRequestParams);

    Optional<IntellijTip> findIntellijTipById(Long id);

    IntellijTip addIntellijTip(IntellijTip intellijTip);

    IntellijTip updateIntellijTip(Long id, IntellijTip intellijTip);

    void deleteIntellijTip(Long id);

    boolean isIntellijTipExist(Long id);
}
