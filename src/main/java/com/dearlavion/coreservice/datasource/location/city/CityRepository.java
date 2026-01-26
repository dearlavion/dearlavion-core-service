package com.dearlavion.coreservice.datasource.location.city;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CityRepository extends MongoRepository<City, String> {

    // 🔹 Find cities by country code
    List<City> findTop10ByCountryCodeOrderByPopulationDesc(String countryCode);

    // 🔹 Find cities by country name (optional)
    List<City> findTop50ByCountryNameOrderByPopulationDesc(String countryName);

    // 🔹 Find cities by city name (autocomplete, all countries)
    List<City> findTop20ByNameIgnoreCaseStartingWith(String name);

    // 🔹 Find cities by city name AND country code (autocomplete filtered)
    List<City> findTop20ByNameIgnoreCaseStartingWithAndCountryCode(String name, String countryCode);

    // 🔹 Fallback contains search
    List<City> findTop20ByNameIgnoreCaseContaining(String name);
}
