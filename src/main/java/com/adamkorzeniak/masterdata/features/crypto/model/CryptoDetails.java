package com.adamkorzeniak.masterdata.features.crypto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CryptoDetails {

    public String symbol;
    public String name;
    public Double price;
    private Long maxSupply;
    private Long circulatingSupply;
    private Long totalSupply;
    private Long coinMarketCapRank;
    private Double volume24h;
    private Double percentChange1h;
    private Double percentChange24h;
    private Double percentChange7d;
    private Double marketCap;
}
