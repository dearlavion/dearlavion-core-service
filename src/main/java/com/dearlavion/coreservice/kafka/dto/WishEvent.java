package com.dearlavion.coreservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishEvent {

    private String id;
    private String username;
    private String title;
    private String countryCode;
    private String cityName;
}
