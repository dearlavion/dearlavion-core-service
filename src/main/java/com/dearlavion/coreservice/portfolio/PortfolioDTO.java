package com.dearlavion.coreservice.portfolio;

import java.util.Date;

public class PortfolioDTO {

    private String id;
    private String userName;
    private String title;
    private String body;
    private String link;
    private boolean isHidden;
    private String image;

    private Date createdAt;
    private Date updatedAt;

    public PortfolioDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public boolean isHidden() { return isHidden; }
    public void setHidden(boolean hidden) { isHidden = hidden; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
