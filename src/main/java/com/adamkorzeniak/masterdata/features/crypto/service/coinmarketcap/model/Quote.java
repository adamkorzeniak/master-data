package com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quote {

    private Double price;

    @JsonProperty("volume_24h")
    private Double volume24h;

    @JsonProperty("percent_change_1h")
    private Double percentChange1h;

    @JsonProperty("percent_change_24h")
    private Double percentChange24h;

    @JsonProperty("percent_change_7d")
    private Double percentChange7d;

    @JsonProperty("market_cap")
    private Double marketCap;

}
