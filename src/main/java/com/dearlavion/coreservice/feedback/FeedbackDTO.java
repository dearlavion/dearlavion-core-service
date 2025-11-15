package com.dearlavion.coreservice.feedback;

import java.util.Date;

public class FeedbackDTO {
    private String id;
    private String reputationId;
    private String type;
    private String userId;
    private String wishId;
    private String comment;
    private Boolean upVoted;
    private Boolean downVoted;
    private Date createdAt;

    public FeedbackDTO() {}

    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }

    public String getReputationId() { return this.reputationId; }
    public void setReputationId(String reputationId) { this.reputationId = reputationId; }

    public String getType() { return this.type; }
    public void setType(String type) { this.type = type; }

    public String getUserId() { return this.userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getWishId() { return this.wishId; }
    public void setWishId(String wishId) { this.wishId = wishId; }

    public String getComment() { return this.comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Boolean getUpVoted() { return this.upVoted; }
    public void setUpVoted(Boolean upVoted) { this.upVoted = upVoted; }

    public Boolean getDownVoted() { return this.downVoted; }
    public void setDownVoted(Boolean downVoted) { this.downVoted = downVoted; }

    public Date getCreatedAt() { return this.createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
