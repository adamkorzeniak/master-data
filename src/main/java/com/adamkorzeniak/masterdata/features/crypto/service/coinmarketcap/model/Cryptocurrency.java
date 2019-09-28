package com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Cryptocurrency {

    private String name;
    private String symbol;

    @JsonProperty("max_supply")
    private Long maxSupply;

    @JsonProperty("circulating_supply")
    private Long circulatingSupply;

    @JsonProperty("total_supply")
    private Long totalSupply;

    @JsonProperty("cmc_rank")
    private Long cmcRank;

    private Map<String, Quote> quote;
}
