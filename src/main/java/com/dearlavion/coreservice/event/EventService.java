package com.dearlavion.coreservice.event;

import com.dearlavion.coreservice.event.search.EventSearchRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventService {
    EventDTO create(EventDTO dto);
    EventDTO update(String id, EventDTO dto);
    void delete(String id);
    Optional<EventDTO> findById(String id);
    List<EventDTO> findAllByUsername(String username);
    EventDTO patch(String id, Map<String, Object> updates) throws JsonMappingException;
    Page<Event> search(EventSearchRequest request);
}
