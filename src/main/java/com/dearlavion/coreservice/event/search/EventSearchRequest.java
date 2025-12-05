package com.dearlavion.coreservice.event.search;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class EventSearchRequest {

    private String keyword;            // title/body search
    private String location;
    private List<String> categories;
    private String status;
    private String rateType;           // FREE / PAID
    private String organizer;          // username of creator
    private Instant startDateFrom;
    private Instant startDateTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private int page = 0;
    private int size = 10;
    private String sortBy; // createdAtDesc, startDateAsc, startDateDesc
}
