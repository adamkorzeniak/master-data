package com.adamkorzeniak.masterdata.features.crypto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoAssetValue {

    public String symbol;
    public String name;
    public Double amount;
    public Double price;
    public Double value;

    public CryptoAssetValue(CryptoDetails cryptoDetails, Double amount) {
        this.symbol = cryptoDetails.getSymbol();
        this.name = cryptoDetails.getName();
        this.amount = amount;
        this.price = cryptoDetails.getPrice();
        this.value = this.price * this.amount;
    }
}
