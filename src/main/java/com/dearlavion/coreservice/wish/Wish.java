package com.dearlavion.coreservice.wish;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wish")
public class Wish {
    @Id
    private String id; // Primary key
    private String username; // FK to user, can be many
    private String image; // Wish image URL
    private String title; // Wish title
    private String body; // Wish description
    //private String location; // Location
    private List<String> categories; // Category name or id
    private String status; // OPEN/ONGOING/COMPLETED
    private Date startDate; // Start date
    private Date endDate; // End date
    private Date createdAt; // Created timestamp
    private Date updatedAt; // Updated timestamp
    private String rateType;
    private BigDecimal amount;
    private List<WishRequestDTO> wishRequestList;
    private String countryCode;      // ISO country code
    private String countryName;
    private String cityName;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private double[] geoPoints; // [longitude, latitude] order matters

}
