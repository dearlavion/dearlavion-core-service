package com.dearlavion.coreservice.wish;

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
@Document(collection = "wish")
public class Wish {
    @Id
    private String id; // Primary key
    private String username; // FK to user, can be many
    private String image; // Wish image URL
    private String title; // Wish title
    private String body; // Wish description
    private String location; // Location
    private String[] categories; // Category name or id
    private String status; // OPEN/ONGOING/COMPLETED
    private Date startDate; // Start date
    private Date endDate; // End date
    private Date createdAt; // Created timestamp
    private Date updatedAt; // Updated timestamp
    private String rateType;
    private String amount;
    private String copilotName;
    private String requestId;
    private String[] requests;

}
