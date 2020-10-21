package com.adamkorzeniak.masterdata.api.features.crypto.service;

import com.adamkorzeniak.masterdata.api.features.crypto.model.CoinMarketCapResponse;
import com.adamkorzeniak.masterdata.api.features.crypto.model.CryptoDetails;
import com.adamkorzeniak.masterdata.api.features.crypto.model.CryptoShort;
import com.adamkorzeniak.masterdata.api.features.crypto.repository.CryptoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CryptoServiceImpl implements CryptoService {

    @Autowired
    private CryptoDetailsMapper cryptoDetailsMapper;

    @Autowired
    private CryptoRepository cryptoRepository;

//    String host = "https://pro-api.coinmarketcap.com";
//    String key = "";

    String host = "https://sandbox-api.coinmarketcap.com";
    String key = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c";

    @Override
    public Set<String> importCryptoDetails(int limit) {
        RestTemplate restTemplate = new RestTemplate();
        String mapUrl = host + "/v1/cryptocurrency/map";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c");

        HttpEntity<Void> request = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(mapUrl)
                .queryParam("limit", 200)
                .queryParam("sort", "cmc_rank");

        ResponseEntity<CoinMarketCapResponse<List<CryptoShort>>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {});

        List<CryptoDetails> cryptoDetails = response.getBody().getData().stream()
                .map(cryptoDetailsMapper::sourceToDestination)
                .collect(Collectors.toList());

        cryptoRepository.save(cryptoDetails);

        return cryptoDetails.stream()
                .map(CryptoDetails::getSymbol)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> importCryptoDetails(Set<String> symbolsToImport) {
        RestTemplate restTemplate = new RestTemplate();
        String mapUrl = host + "/v1/cryptocurrency/map";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c");

        HttpEntity<Void> request = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(mapUrl)
                .queryParam("symbols", String.join(",", symbolsToImport));

        ResponseEntity<CoinMarketCapResponse<List<CryptoShort>>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {});

        List<CryptoDetails> cryptoDetails = response.getBody().getData().stream()
                .map(cryptoDetailsMapper::sourceToDestination)
                .collect(Collectors.toList());

        cryptoRepository.save(cryptoDetails);

        return cryptoDetails.stream()
                .map(CryptoDetails::getSymbol)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> getExistingCryptoSymbols() {
        return cryptoRepository.findAll().stream()
                .map(CryptoDetails::getSymbol)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

}
