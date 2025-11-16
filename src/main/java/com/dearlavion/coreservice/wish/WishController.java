package com.dearlavion.coreservice.wish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wish")
public class WishController {
    @Autowired
    private WishService service;

    @GetMapping
    public List<WishDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<WishDTO> getById(@PathVariable String id) {
        System.out.println("here");
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public WishDTO create(@RequestBody WishDTO dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public WishDTO update(@PathVariable String id, @RequestBody WishDTO dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
