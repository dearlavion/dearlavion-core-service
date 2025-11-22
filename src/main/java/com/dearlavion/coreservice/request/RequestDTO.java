package com.dearlavion.coreservice.request;

import java.util.Date;

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

    public RequestDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getWishId() { return wishId; }
    public void setWishId(String wishId) { this.wishId = wishId; }
    public String getWisherName() { return wisherName; }
    public void setWisherName(String wisherName) { this.wisherName = wisherName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }
    public String[] getPortfolios() { return portfolios; }
    public void setPortfolios(String[] portfolios) { this.portfolios = portfolios; }
    public String getRateType() { return rateType; }
    public void setRateType(String rateType) { this.rateType = rateType; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}