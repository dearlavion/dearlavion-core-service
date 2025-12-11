package com.dearlavion.coreservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    private String id;
    private String wishId;
    private String wisherName;
    private String username;
    private String title;
    private String body;
    private String status; // e.g. PENDING / ACCEPTED / DECLINED

    private boolean accepted;

    private String[] portfolios;
    private String rateType;
    private String amount;

    private Date createdAt;
    private Date updatedAt;
    private String image;
}