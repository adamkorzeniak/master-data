package com.adamkorzeniak.masterdata.api.features.crypto.controller;

import com.adamkorzeniak.masterdata.api.features.crypto.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/api/feature/Crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> importCryptoDetails(
            @RequestParam(value = "limit", defaultValue = "200") Integer limit,
            @RequestParam(value = "symbols") String symbols,
            @RequestParam(value = "sync", defaultValue = "true") Boolean syncExisting
    ) {

        Set<String> cryptosToImport = getCryptosToImport(symbols, syncExisting);

        Set<String> cryptosImported = importTopCryptos(limit);

        cryptosToImport.removeAll(cryptosImported);
        if (!cryptosToImport.isEmpty()) {
            cryptoService.importCryptoDetails(cryptosToImport);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private Set<String> getCryptosToImport(String symbols, Boolean syncExisting) {
        Set<String> symbolsToImport = new HashSet<>();
        if (symbols != null) {
            symbolsToImport.addAll(new HashSet<>(Arrays.asList(symbols.toUpperCase().split(", "))));
        }
        if (syncExisting) {
            List<String> existingCryptos = cryptoService.getExistingCryptoSymbols();
            symbolsToImport.addAll(new HashSet<>(Arrays.asList(symbols.toUpperCase().split(", "))));
        }
        return symbolsToImport;
    }

    private Set<String> importTopCryptos(Integer limit) {
        if (limit > 0) {
            return cryptoService.importCryptoDetails(limit);
        }
        return new HashSet<>();
    }
}
