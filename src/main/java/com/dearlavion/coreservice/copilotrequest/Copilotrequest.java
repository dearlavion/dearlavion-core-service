package com.dearlavion.coreservice.copilotrequest;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "copilotrequest")
public class Copilotrequest {
    private String id; // Primary key
    private String wishId; // FK to wish
    private String userId; // Requester userId
    private String message; // Request message
    private String status; // PENDING/ACCEPTED/REJECTED
    private Date createdAt; // Created timestamp

    public Copilotrequest() {}

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getWishId() { return this.wishId; }

    public void setWishId(String wishId) { this.wishId = wishId; }

    public String getUserId() { return this.userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getMessage() { return this.message; }

    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return this.createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
