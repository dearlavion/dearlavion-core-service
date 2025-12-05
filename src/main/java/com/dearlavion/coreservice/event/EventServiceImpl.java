package com.dearlavion.coreservice.event;

import com.dearlavion.coreservice.event.search.EventSearchRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    // CREATE EVENT
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

    // PARTIAL UPDATE (PATCH)
    @Override
    public EventDTO patch(String id, Map<String, Object> updates) throws JsonMappingException {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        updates.forEach((key, value) -> applyPatch(event, key, value));

        event.setUpdatedAt(LocalDateTime.now());
        Event saved = eventRepository.save(event);

        return mapper.map(saved, EventDTO.class);
    }

    @Override
    public Page<Event> search(EventSearchRequest request) {
        return eventRepository.searchEvents(request);
    }

    private void applyPatch(Event event, String key, Object value) {

        switch (key) {

            case "title" -> event.setTitle((String) value);
            case "body" -> event.setBody((String) value);
            case "location" -> event.setLocation((String) value);

            case "categories" ->
                    event.setCategories((List<String>) value);

            case "startDate" ->
                    event.setStartDate(LocalDateTime.parse(value.toString()));

            case "endDate" ->
                    event.setEndDate(LocalDateTime.parse(value.toString()));

            case "rateType" ->
                    event.setRateType((String) value);

            case "amount" ->
                    event.setAmount((String) value);

            case "image" ->
                    event.setImage((String) value);

            case "participantLimit" ->
                    event.setParticipantLimit((Integer) value);

            case "status" ->
                    event.setStatus((String) value);

            case "eventRequestList" ->
                    event.setEventRequestList((List<EventRequestDTO>) value);

            default ->
                    System.out.println("Unknown patch field: " + key);
        }
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


    /*
    @Override
    public EventDTO addParticipant(String eventId, String userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check max capacity
        if (event.getParticipantLimit() != null &&
                event.getParticipantCount() >= event.getParticipantLimit()) {
            throw new RuntimeException("Event is full");
        }

        if (event.getEventRequestList() == null)
            event.setEventRequestList(new ArrayList<>());

        // Add participant record
        EventRequestDTO req = new EventRequestDTO();
        req.setUserId(userId);
        req.setStatus("ACCEPTED");
        req.setCreatedAt(LocalDateTime.now());

        event.getEventRequestList().add(req);

        event.setParticipantCount(event.getParticipantCount() + 1);

        if (event.getParticipantLimit() != null &&
                event.getParticipantCount() >= event.getParticipantLimit()) {
            event.setStatus("FULL");
        }

        event.setUpdatedAt(LocalDateTime.now());
        Event saved = eventRepository.save(event);

        return mapper.map(saved, EventDTO.class);
    }

     */


}
