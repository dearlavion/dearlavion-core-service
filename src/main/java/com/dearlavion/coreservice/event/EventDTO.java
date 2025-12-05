package com.dearlavion.coreservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private String id;
    private String username;
    private String title;
    private String body;
    private String location;
    private List<String> categories;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String rateType;
    private String amount;
    private String image;
    private Integer participantLimit;
    private Integer participantCount;
    private String status;
    private List<EventRequestDTO> eventRequestList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
