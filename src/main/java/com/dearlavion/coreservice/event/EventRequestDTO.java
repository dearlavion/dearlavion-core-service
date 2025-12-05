package com.dearlavion.coreservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {

    private String requestId;
    private String wisherName;
    private String status; // PENDING | ACCEPTED | COMPLETED | REJECTED | CANCELLED
}

