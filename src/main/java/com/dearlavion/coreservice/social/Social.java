package com.dearlavion.coreservice.social;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "social")
public class Social {
    private String id; // Primary key
    private String userId; // FK to user
    private String platform; // Platform name like FACEBOOK
    private String value; // Handle
    private String link; // Profile URL
    private Boolean isVerified; // Verified flag

    public Social() {}

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getUserId() { return this.userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getPlatform() { return this.platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public String getValue() { return this.value; }

    public void setValue(String value) { this.value = value; }

    public String getLink() { return this.link; }

    public void setLink(String link) { this.link = link; }

    public Boolean getIsVerified() { return this.isVerified; }

    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }
}
