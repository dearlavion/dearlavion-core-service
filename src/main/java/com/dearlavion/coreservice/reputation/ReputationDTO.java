package com.dearlavion.coreservice.reputation;

public class ReputationDTO {
    private String id;
    private String userId;
    private Integer upVoteCount;
    private Integer downVoteCount;
    private Integer score;
    private Date createdAt;
    private Date updatedAt;

    public ReputationDTO() {}

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
