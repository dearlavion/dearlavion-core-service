package com.dearlavion.coreservice.wish;

import java.util.Date;

public class WishDTO {
    private String id;
    private String username;
    private String image;
    private String title;
    private String body;
    private String location;
    private String[] categories;
    private String status;
    private String rateType;
    private String amount;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
    private String copilotName;
    private String requestId;
    private String[] requests;

    public WishDTO() {}

    public String[] getCategories() {return categories;}
    public void setCategories(String[] categories) {this.categories = categories;}
    public String getRateType() { return rateType; }
    public void setRateType(String rateType) { this.rateType = rateType; }
    public String getAmount() {return amount;}
    public void setAmount(String amount) {this.amount = amount;}
    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getImage() { return this.image; }
    public void setImage(String image) { this.image = image; }
    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return this.body; }
    public void setBody(String body) { this.body = body; }
    public String getLocation() { return this.location; }
    public void setLocation(String location) { this.location = location; }
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
    public String getCopilotName() { return copilotName; }
    public void setCopilotName(String copilotName) { this.copilotName = copilotName; }
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public String[] getRequests() { return requests; }
    public void setRequests(String[] requests) { this.requests = requests;}
}
