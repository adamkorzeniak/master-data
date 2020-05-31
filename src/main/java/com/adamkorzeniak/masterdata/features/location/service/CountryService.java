package com.adamkorzeniak.masterdata.features.location.service;

import com.adamkorzeniak.masterdata.features.location.model.Country;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CountryService {

    List<Country> searchCountrys(Map<String, String> allRequestParams);

    Optional<Country> findCountryById(Long id);

    Country addCountry(Country country);

    Country updateCountry(Long id, Country country);

    void deleteCountry(Long id);

    boolean isCountryExist(Long id);
}
