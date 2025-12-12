package com.dearlavion.coreservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "request")
public class Request {
    @Id
    private String id;
    private String username;
    private String targetId;
    private String targetOwner;
    private String title;
    private String body;
    private String status;
    private boolean accepted;
    private String[] portfolios;
    private String rateType;
    private BigDecimal amount;
    private String image;
    private Date createdAt;
    private Date updatedAt;
    private String requestType;
    private String paymentStatus;
    private String transactionId;
    private boolean paid;
}