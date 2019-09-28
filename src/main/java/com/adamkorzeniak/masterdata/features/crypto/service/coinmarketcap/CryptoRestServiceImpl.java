package com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap;

import com.adamkorzeniak.masterdata.features.crypto.service.coinmarketcap.model.CoinMarketCapPricingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CryptoRestServiceImpl implements CryptoRestService {

    private static final String COINMARKETCAP_API_HOST_URL = "https://sandbox-api.coinmarketcap.com";
    private static final String COINMARKETCAP_API_PRICING_PATH = "/v1/cryptocurrency/quotes/latest";

    private final Environment env;

    @Autowired
    public CryptoRestServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public CoinMarketCapPricingResponse getCryptoPricingResponse(List<String> cryptoSymbols, String currencyCode) {

        String symbols = String.join(",", cryptoSymbols);
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = COINMARKETCAP_API_HOST_URL
            + COINMARKETCAP_API_PRICING_PATH
            + "?symbol=" + symbols
            + "&convert=" + currencyCode;
        String apiKey = env.getProperty("coinmarketcap.security.apikey");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<CoinMarketCapPricingResponse> respEntity = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, CoinMarketCapPricingResponse.class);
        return respEntity.getBody();
    }
}
