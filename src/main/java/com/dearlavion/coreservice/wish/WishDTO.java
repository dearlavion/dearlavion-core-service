package com.dearlavion.coreservice.wish;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishDTO {
    private String id;
    private String username;
    private String image;
    private String title;
    private String body;
    private String location;
    private List<String> categories;
    private String status;
    private String rateType;
    private BigDecimal amount;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
    private List<WishRequestDTO> wishRequestList;
    private String countryCode;      // ISO country code
    private String countryName;
    private String cityName;
    private Double latitude;
    private Double longitude;
}
