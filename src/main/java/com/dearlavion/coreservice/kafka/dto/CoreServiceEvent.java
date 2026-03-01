package com.dearlavion.coreservice.kafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoreServiceEvent {

    private EventType type;      // WISH, REQUEST, etc.
    private Object payload;   // actual event data
}
