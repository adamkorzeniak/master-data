package com.adamkorzeniak.masterdata.features.location.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.location.model.Country;
import com.adamkorzeniak.masterdata.features.location.repository.CountryRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository,
                            ApiQueryService apiQueryService) {
        this.countryRepository = countryRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Country> searchCountrys(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Country> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return countryRepository.findAll(spec);
    }

    @Override
    public Optional<Country> findCountryById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country addCountry(Country country) {
        country.setId(-1L);
        return countryRepository.save(country);
    }

    @Override
    public Country updateCountry(Long id, Country country) {
        country.setId(id);
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }

    @Override
    public boolean isCountryExist(Long id) {
        return countryRepository.existsById(id);
    }
}
