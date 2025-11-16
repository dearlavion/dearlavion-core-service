package com.dearlavion.coreservice.copilotrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request")
public class CopilotrequestController {
    @Autowired
    private CopilotrequestService service;

    @GetMapping
    public List<CopilotrequestDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<CopilotrequestDTO> getById(@PathVariable String id) { return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public CopilotrequestDTO create(@RequestBody CopilotrequestDTO dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public CopilotrequestDTO update(@PathVariable String id, @RequestBody CopilotrequestDTO dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
