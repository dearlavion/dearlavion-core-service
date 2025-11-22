package com.dearlavion.coreservice.portfolio;


import java.util.Date;

public class PortfolioDTO {

    private String id;
    private String username;
    private String title;
    private String body;
    private String link;

    private boolean hidden;
    private String image;

    private Date createdAt;
    private Date updatedAt;

    public PortfolioDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden;}
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
