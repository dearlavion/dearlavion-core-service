package com.dearlavion.coreservice.wish.search;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class WishSearchRequest {
    private String keyword;          // search in title/body
    private String location;
    private List<String> categories; // multiple categories
    private String status;           // OPEN/ONGOING/COMPLETED
    private String rateType;         // FREE/PAID
    private String username;         // filter owner
    private Instant startDateFrom;
    private Instant startDateTo;
    private int page = 0;
    private int size = 10;
}
