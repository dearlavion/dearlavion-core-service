package com.dearlavion.coreservice.social;

public class SocialDTO {
    private String id;
    private String userId;
    private String platform;
    private String value;
    private String link;
    private Boolean isVerified;

    public SocialDTO() {}

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
