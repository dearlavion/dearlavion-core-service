package com.dearlavion.coreservice.datasource.location.city;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<City> getCitiesByCountry(String countryCode) {
        return repository.findTop10ByCountryCodeOrderByPopulationDesc(countryCode);
    }

    public List<City> searchCity(String query, String countryCode) {
        if (query == null || query.length() < 2) return List.of();

        if (countryCode != null && !countryCode.isBlank()) {
            return repository.findTop20ByNameIgnoreCaseStartingWithAndCountryCode(query, countryCode);
        } else {
            return repository.findTop20ByNameIgnoreCaseStartingWith(query);
        }
    }
}
