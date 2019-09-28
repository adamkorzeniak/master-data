package com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap;

import com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model.CoinMarketCapPricingResponse;

import java.util.List;

public interface CryptoRestService {

    CoinMarketCapPricingResponse getCryptoPricingResponse(List<String> cryptoSymbols, String currencyCode);

}
