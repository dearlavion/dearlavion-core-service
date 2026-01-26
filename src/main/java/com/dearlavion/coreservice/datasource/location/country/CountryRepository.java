package com.dearlavion.coreservice.datasource.location.country;

import com.dearlavion.coreservice.datasource.location.city.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends MongoRepository<City, String> {
    // Not strictly needed if you use aggregation via MongoTemplate
}

