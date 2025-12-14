package com.dearlavion.coreservice.event;

import com.dearlavion.coreservice.event.search.EventSearchRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public EventDTO create(EventDTO dto) {
        Event event = mapper.map(dto, Event.class);

        event.setId(null);
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        event.setParticipantCount(0);

        if (event.getStatus() == null) {
            event.setStatus("OPEN");
        }

        Event saved = eventRepository.save(event);
        return mapper.map(saved, EventDTO.class);
    }

    @Override
    public EventDTO update(String id, EventDTO dto) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Map DTO → entity but keep ID + createdAt
        Event updated = mapper.map(dto, Event.class);
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setUpdatedAt(LocalDateTime.now());

        // Protect participantCount
        updated.setParticipantCount(existing.getParticipantCount());

        Event saved = eventRepository.save(updated);
        return mapper.map(saved, EventDTO.class);
    }

    @Override
    public Page<Event> search(EventSearchRequest request) {
        return eventRepository.searchEvents(request);
    }

    @Override
    public void delete(String id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Optional<EventDTO> findById(String id) {
        return eventRepository.findById(id).map(e -> mapper.map(e, EventDTO.class));
    }

    @Override
    public List<EventDTO> findAllByUsername(String username) {
        return eventRepository.findAllByUsername(username)
                .stream()
                .map(e -> mapper.map(e, EventDTO.class))
                .toList();
    }

    @Override
    public EventDTO patch(String id, Map<String, Object> updates) throws JsonMappingException {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Extract request operations:
        Object requestIdObj = updates.remove("requestId");
        String requestId = requestIdObj != null ? requestIdObj.toString() : null;

        Object wisherNameObj = updates.remove("wisherName");
        String wisherName = wisherNameObj != null ? wisherNameObj.toString() : null;

        Object statusObj = updates.remove("status");
        String status = statusObj != null ? statusObj.toString() : null;

        Object removeRequestObj = updates.remove("removeRequest");
        boolean removeRequest = removeRequestObj != null && Boolean.parseBoolean(removeRequestObj.toString());

        Object eventCompleteObj = updates.remove("eventComplete");
        boolean eventComplete = eventCompleteObj != null && Boolean.parseBoolean(eventCompleteObj.toString());

        Object eventReOpenObj = updates.remove("eventReOpen");
        boolean eventReOpen = eventReOpenObj != null && Boolean.parseBoolean(eventReOpenObj.toString());

        // Merge normal fields
        objectMapper.updateValue(event, updates);

        // Initialize list if null
        if (event.getEventRequestList() == null)
            event.setEventRequestList(new ArrayList<>());

        if (removeRequest) {
            event.getEventRequestList().removeIf(er -> requestId.equals(er.getRequestId()));
        } else if (eventComplete) {
            updateEventToComplete(event);
        } else if (eventReOpen) {
            reOpenEvent(event);
        } else {
            updateEventRequestList(event, requestId, wisherName, status);
        }

        event.setUpdatedAt(LocalDateTime.now());
        Event saved = eventRepository.save(event);

        return mapper.map(saved, EventDTO.class);
    }

    private void reOpenEvent(Event event) {
        event.setStatus("OPEN");
    }

    private void updateEventToComplete(Event event) {
        event.getEventRequestList().forEach(er -> {
            String newStatus = "ONGOING".equals(er.getStatus())
                    ? "COMPLETED"
                    : "REJECTED";

            er.setStatus(newStatus);
        });

        event.setStatus("COMPLETED");
    }

    private void updateEventRequestList(Event event, String requestId, String wisherName, String status) {

        Optional<EventRequestDTO> existing = event.getEventRequestList().stream()
                .filter(er -> er.getRequestId().equals(requestId))
                .findFirst();

        if (existing.isEmpty()) {

            EventRequestDTO er = new EventRequestDTO();
            er.setRequestId(requestId);
            er.setWisherName(wisherName);
            er.setStatus(status);

            event.getEventRequestList().add(er);
            //Update participant count
            Integer count = event.getParticipantCount();
            if("ONGOING".equals(status)) {
                event.setParticipantCount(count + 1);
            }

        } else {
            EventRequestDTO er = existing.get();
            if (status != null) er.setStatus(status);
            Integer count = event.getParticipantCount();
            if ("CANCELLED".equals(status)) {
                event.setParticipantCount(count - 1);
            } else if("ONGOING".equals(status)) {
                event.setParticipantCount(count + 1);
            }
        }

        updateEventStatus(event);
    }

    private void updateEventStatus(Event event) {

        boolean anyOngoing = event.getEventRequestList().stream()
                .anyMatch(er -> "ONGOING".equals(er.getStatus()));

        if (anyOngoing) {
            event.setStatus("ONGOING");
        } else {
            event.setStatus("OPEN");
        }
    }





}
