package com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CoinMarketCapPricingResponse {

    private Status status;
    private Map<String, Cryptocurrency> data;

}
