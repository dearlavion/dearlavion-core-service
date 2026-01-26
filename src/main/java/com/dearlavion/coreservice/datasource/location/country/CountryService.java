package com.dearlavion.coreservice.datasource.location.country;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final MongoTemplate mongoTemplate;

    public CountryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<CountryOption> getCountries() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("countryCode", "countryName"),
                Aggregation.sort(Sort.by("countryName"))
        );

        AggregationResults<Document> results =
                mongoTemplate.aggregate(agg, "countries", Document.class);

        return results.getMappedResults()
                .stream()
                .map(d -> new CountryOption(
                        ((Document) d.get("_id")).getString("countryCode"),
                        ((Document) d.get("_id")).getString("countryName")
                ))
                .toList();
    }
}

