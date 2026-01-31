package com.dearlavion.coreservice.datasource.location.city;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "countries")
public class City {

    @Id
    private String id; // MongoDB _id

    private String name; // city name
    private String countryName;
    private String countryCode;
    private long population;
    private Double latitude;
    private Double longitude;
}
