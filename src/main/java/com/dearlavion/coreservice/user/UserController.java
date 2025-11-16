package com.dearlavion.coreservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) { return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) { return service.create(dto); }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserDTO dto) { return service.update(id, dto); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
