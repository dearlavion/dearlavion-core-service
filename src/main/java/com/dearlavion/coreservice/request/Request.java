package com.dearlavion.coreservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "request")
public class Request {

    @Id
    private String id;

    private String wishId;
    private String userId;
    private String message;
    private String status;

    private boolean accepted;

    private String[] portfolios;
    private String rateType;
    private String amount;

    private Date createdAt;
    private Date updatedAt;
}