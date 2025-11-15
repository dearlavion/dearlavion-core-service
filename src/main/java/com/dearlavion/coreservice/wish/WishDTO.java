package com.dearlavion.coreservice.wish;

import java.util.Date;

public class WishDTO {
    private String id;
    private String userId;
    private String image;
    private String title;
    private String body;
    private String location;
    private String category;
    private String status;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;

    public WishDTO() {}

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
