package com.dearlavion.coreservice.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Social")
@RestController
@RequestMapping("/api/social")
public class SocialController {
    @Autowired
    private SocialService service;

    @GetMapping
    public List<SocialDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<SocialDTO> getById(@PathVariable String id) { return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public SocialDTO create(@RequestBody SocialDTO dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public SocialDTO update(@PathVariable String id, @RequestBody SocialDTO dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
