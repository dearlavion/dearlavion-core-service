package com.dearlavion.coreservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private String id;
    private String username;
    private String targetId;
    private String targetOwner;
    private String title;
    private String body;
    private String status; //ENUM('OPEN','ONGOING','COMPLETED','REJECTED','CANCELLED')
    private boolean accepted;
    private String[] portfolios;
    private String rateType; //ENUM('FREE', 'PAID')
    private BigDecimal amount;
    private String image;
    private Date createdAt;
    private Date updatedAt;
    private String requestType; //ENUM('wish', 'event')
    private String paymentStatus; //ENUM('FAILED','PROCESSING','ACCEPTED','NONE')
    private String transactionId;
    private boolean paid;
}