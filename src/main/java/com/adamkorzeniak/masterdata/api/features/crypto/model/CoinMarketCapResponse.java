package com.adamkorzeniak.masterdata.api.features.crypto.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CoinMarketCapResponse<T> implements Serializable {

    private Status status;
    private T data;
}
