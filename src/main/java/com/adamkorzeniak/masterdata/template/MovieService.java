package com.adamkorzeniak.masterdata.template;

import com.adamkorzeniak.masterdata.features.qwertyP.model.Qwerty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QwertyService {

    List<Qwerty> searchQwertys(Map<String, String> allRequestParams);

    Optional<Qwerty> findQwertyById(Long id);

    Qwerty addQwerty(Qwerty qwerty);

    Qwerty updateQwerty(Long id, Qwerty qwerty);

    void deleteQwerty(Long id);

    boolean isQwertyExist(Long id);
}
