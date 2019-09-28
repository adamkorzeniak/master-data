package com.adamkorzeniak.masterdata.features.crypto.service;

import com.adamkorzeniak.masterdata.features.crypto.model.CryptoAsset;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptoAssetValue;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptoDetails;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptoPortfolio;
import com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.CryptoRestService;
import com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model.CoinMarketCapPricingResponse;
import com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model.Cryptocurrency;
import com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRestService cryptoRestService;

    @Autowired
    public CryptoServiceImpl(CryptoRestService cryptoRestService) {
        this.cryptoRestService = cryptoRestService;
    }

    @Override
    public List<CryptoDetails> getCryptoDetails(String symbols, String currencyCode) {
        List<String> cryptoSymbols = Arrays.asList(symbols.split("-"));
        return extractCryptoDetails(cryptoRestService.getCryptoPricingResponse(cryptoSymbols, currencyCode));
    }

    @Override
    public CryptoPortfolio generateCryptoPortfolio(List<CryptoAsset> assets, String currencyCode) {
        List<String> cryptoSymbols = assets.stream()
            .map(CryptoAsset::getSymbol)
            .collect(Collectors.toList());
        CoinMarketCapPricingResponse cryptoPricingResponse = cryptoRestService.getCryptoPricingResponse(cryptoSymbols, currencyCode);
        List<CryptoDetails> cryptoDetails = extractCryptoDetails(cryptoPricingResponse);
        List<CryptoAssetValue> assetsValues = generateAssetValues(cryptoDetails, assets);
        return new CryptoPortfolio(assetsValues, currencyCode);
    }

    private List<CryptoAssetValue> generateAssetValues(List<CryptoDetails> cryptoDetails, List<CryptoAsset> assets) {
        return cryptoDetails.stream()
            .map(cryptoDetail -> {
                String symbol = cryptoDetail.getSymbol();
                CryptoAsset cryptoAsset = assets.stream().filter(asset -> symbol.equals(asset.getSymbol())).findFirst().orElseThrow();
                return new CryptoAssetValue(cryptoDetail, cryptoAsset.getAmount());
            })
            .collect(Collectors.toList());
    }

    private List<CryptoDetails> extractCryptoDetails(CoinMarketCapPricingResponse response) {

        List<CryptoDetails> cryptoPrices = new ArrayList<>();
        Map<String, Cryptocurrency> cryptos = response.getData();

        for (Map.Entry<String, Cryptocurrency> entry : cryptos.entrySet()) {
            Cryptocurrency cryptoResponse = entry.getValue();

            CryptoDetails cryptoDetails = new CryptoDetails();
            cryptoDetails.setName(cryptoResponse.getName());
            cryptoDetails.setSymbol(cryptoResponse.getSymbol());
            cryptoDetails.setMaxSupply(cryptoResponse.getMaxSupply());
            cryptoDetails.setCirculatingSupply(cryptoResponse.getCirculatingSupply());
            cryptoDetails.setTotalSupply(cryptoResponse.getTotalSupply());
            cryptoDetails.setCoinMarketCapRank(cryptoResponse.getCmcRank());

            Quote quote = cryptoResponse.getQuote().entrySet().iterator().next().getValue();
            cryptoDetails.setPrice(quote.getPrice());
            cryptoDetails.setPercentChange1h(quote.getPercentChange1h());
            cryptoDetails.setPercentChange24h(quote.getPercentChange24h());
            cryptoDetails.setPercentChange7d(quote.getPercentChange7d());
            cryptoDetails.setMarketCap(quote.getMarketCap());
            cryptoDetails.setVolume24h(quote.getVolume24h());

            cryptoPrices.add(cryptoDetails);
        }
        return cryptoPrices;
    }

}
