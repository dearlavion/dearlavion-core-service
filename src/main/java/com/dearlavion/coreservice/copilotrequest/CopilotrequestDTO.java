package com.dearlavion.coreservice.copilotrequest;

public class CopilotrequestDTO {
    private String id;
    private String wishId;
    private String userId;
    private String message;
    private String status;
    private Date createdAt;

    public CopilotrequestDTO() {}

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
