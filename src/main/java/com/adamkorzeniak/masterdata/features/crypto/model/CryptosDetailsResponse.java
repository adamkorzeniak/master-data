package com.adamkorzeniak.masterdata.features.crypto.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CryptosDetailsResponse {

    private final String currencyCode;
    private final List<CryptoDetails> cryptoDetails;

    public CryptosDetailsResponse(List<CryptoDetails> cryptoDetails, String currencyCode) {
        this.currencyCode = currencyCode;
        this.cryptoDetails = cryptoDetails;
    }
}
