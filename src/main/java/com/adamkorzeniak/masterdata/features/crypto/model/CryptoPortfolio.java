package com.adamkorzeniak.masterdata.features.crypto.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CryptoPortfolio {

    private final Double totalValue;
    private final String currencyCode;
    private final List<CryptoAssetValue> cryptoAssets;

    public CryptoPortfolio(List<CryptoAssetValue> assets, String currencyCode) {
        this.cryptoAssets = assets;
        this.currencyCode = currencyCode;
        this.totalValue = calculateTotalValue(assets);
    }

    private Double calculateTotalValue(List<CryptoAssetValue> assets) {
        return assets
            .stream()
            .mapToDouble(CryptoAssetValue::getValue)
            .sum();
    }

}
