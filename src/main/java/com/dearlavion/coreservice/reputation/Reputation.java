package com.dearlavion.coreservice.reputation;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "reputation")
public class Reputation {
    private String id; // Primary key
    private String userId; // FK to user
    private Integer upVoteCount; // Total upvotes
    private Integer downVoteCount; // Total downvotes
    private Integer score; // Computed score
    private Date createdAt; // Created timestamp
    private Date updatedAt; // Updated timestamp

    public Reputation() {}

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getUserId() { return this.userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public Integer getUpVoteCount() { return this.upVoteCount; }

    public void setUpVoteCount(Integer upVoteCount) { this.upVoteCount = upVoteCount; }

    public Integer getDownVoteCount() { return this.downVoteCount; }

    public void setDownVoteCount(Integer downVoteCount) { this.downVoteCount = downVoteCount; }

    public Integer getScore() { return this.score; }

    public void setScore(Integer score) { this.score = score; }

    public Date getCreatedAt() { return this.createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return this.updatedAt; }

    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
