package com.dearlavion.coreservice.reputation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Reputation")
@RestController
@RequestMapping("/api/reputation")
public class ReputationController {
    @Autowired
    private ReputationService service;

    @GetMapping
    public List<ReputationDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ReputationDTO> getById(@PathVariable String id) { return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public ReputationDTO create(@RequestBody ReputationDTO dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public ReputationDTO update(@PathVariable String id, @RequestBody ReputationDTO dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
