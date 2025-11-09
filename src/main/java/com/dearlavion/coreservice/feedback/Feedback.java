package com.dearlavion.coreservice.feedback;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "feedback")
public class Feedback {
    private String id; // Primary key
    private String reputationId; // FK to reputation
    private String type; // WISHER/COPILOT
    private String userId; // User who gave feedback
    private String wishId; // Associated wish
    private String comment; // Feedback comment
    private Boolean upVoted; // Upvote flag
    private Boolean downVoted; // Downvote flag
    private Date createdAt; // Created timestamp

    public Feedback() {}

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
