package com.dearlavion.coreservice.event;

import com.dearlavion.coreservice.common.PageResponse;
import com.dearlavion.coreservice.event.search.EventSearchRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping
    public EventDTO create(@RequestBody EventDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public EventDTO update(@PathVariable String id, @RequestBody EventDTO dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public EventDTO patch(@PathVariable String id, @RequestBody Map<String, Object> updates) throws JsonMappingException {
        return service.patch(id, updates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{username}")
    public List<EventDTO> getAll(@PathVariable String username) {
        return service.findAllByUsername(username);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable String id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/search")
    public PageResponse<Event> search(@RequestBody EventSearchRequest request) {
        Page<Event> page = service.search(request);
        return new PageResponse<>(page);
    }
}
