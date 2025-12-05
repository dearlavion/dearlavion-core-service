package com.dearlavion.coreservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "event")
public class Event {

    @Id
    private String id;
    private String username;            // creator username
    private String title;
    private String body;                // matches frontend "body"
    private String location;
    private List<String> categories;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String rateType;            // FREE | PAID
    private String amount;              // optional
    private String image;               // URL or base64
    private Integer participantLimit;   // nullable
    private Integer participantCount;
    private String status;              // OPEN | ONGOING | COMPLETED | FULL
    private List<EventRequestDTO> eventRequestList; // embedded objects
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
