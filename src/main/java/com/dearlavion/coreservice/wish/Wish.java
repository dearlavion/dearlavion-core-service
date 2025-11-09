package com.dearlavion.coreservice.wish;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "wish")
public class Wish {
    private String id; // Primary key
    private String userId; // FK to user
    private String image; // Wish image URL
    private String title; // Wish title
    private String body; // Wish description
    private String location; // Location
    private String category; // Category name or id
    private String status; // OPEN/ONGOING/COMPLETED
    private Date startDate; // Start date
    private Date endDate; // End date
    private Date createdAt; // Created timestamp
    private Date updatedAt; // Updated timestamp

    public Wish() {}

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getUserId() { return this.userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getImage() { return this.image; }

    public void setImage(String image) { this.image = image; }

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    public String getBody() { return this.body; }

    public void setBody(String body) { this.body = body; }

    public String getLocation() { return this.location; }

    public void setLocation(String location) { this.location = location; }

    public String getCategory() { return this.category; }

    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    public Date getStartDate() { return this.startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return this.endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public Date getCreatedAt() { return this.createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return this.updatedAt; }

    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
