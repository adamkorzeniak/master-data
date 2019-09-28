package com.adamkorzeniak.masterdata.features.crypto.controller;

import com.adamkorzeniak.masterdata.features.crypto.model.CryptoAsset;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptoDetails;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptoPortfolio;
import com.adamkorzeniak.masterdata.features.crypto.model.CryptosDetailsResponse;
import com.adamkorzeniak.masterdata.features.crypto.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v0/Crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/Prices")
    public CryptosDetailsResponse getCryptoPrices(
        @RequestParam String symbols,
        @RequestParam String currencyCode) {

        List<CryptoDetails> cryptoDetails = cryptoService.getCryptoDetails(symbols, currencyCode);
        return new CryptosDetailsResponse(cryptoDetails, currencyCode);
    }

    @PostMapping("/Portfolio")
    public CryptoPortfolio getCryptoPortfolio(
        @RequestBody List<CryptoAsset> assets,
        @RequestParam String currencyCode) {

        return cryptoService.generateCryptoPortfolio(assets, currencyCode);
    }

}
