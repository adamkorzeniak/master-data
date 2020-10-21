package com.adamkorzeniak.masterdata.api.features.crypto.service;

import java.util.List;
import java.util.Set;

public interface CryptoService {

    Set<String> importCryptoDetails(int limit);

    Set<String> importCryptoDetails(Set<String> symbolsToImport);

    List<String> getExistingCryptoSymbols();
}
