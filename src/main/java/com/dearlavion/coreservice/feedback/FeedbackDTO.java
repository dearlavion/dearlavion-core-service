package com.dearlavion.coreservice.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
