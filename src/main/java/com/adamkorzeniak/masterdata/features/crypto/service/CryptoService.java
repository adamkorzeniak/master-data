package com.adamkorzeniak.masterdata.features.crypto.service;

import com.adamkorzeniak.masterdata.features.crypto.model.CryptoAsset;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptoDetails;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptoPortfolio;

import java.util.List;

public interface CryptoService {

    List<CryptoDetails> getCryptoDetails(String symbols, String currencyCode);

    CryptoPortfolio generateCryptoPortfolio(List<CryptoAsset> assets, String currencyCode);

}
