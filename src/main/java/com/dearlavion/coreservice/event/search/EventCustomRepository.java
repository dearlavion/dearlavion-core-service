package com.dearlavion.coreservice.event.search;

import com.dearlavion.coreservice.event.Event;
import org.springframework.data.domain.Page;

public interface EventCustomRepository {
    Page<Event> searchEvents(EventSearchRequest request);
}
