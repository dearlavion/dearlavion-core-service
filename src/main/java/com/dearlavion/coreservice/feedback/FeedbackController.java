package com.dearlavion.coreservice.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService service;

    @GetMapping
    public List<FeedbackDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getById(@PathVariable String id) { return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public FeedbackDTO create(@RequestBody FeedbackDTO dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public FeedbackDTO update(@PathVariable String id, @RequestBody FeedbackDTO dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
