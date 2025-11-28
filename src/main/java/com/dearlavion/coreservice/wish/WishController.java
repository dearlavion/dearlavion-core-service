package com.dearlavion.coreservice.wish;

import com.dearlavion.coreservice.common.PageResponse;
import com.dearlavion.coreservice.wish.search.WishSearchRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wish")
public class WishController {
    @Autowired
    private WishService service;

    @PostMapping
    public WishDTO create(@RequestBody WishDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public WishDTO update(@PathVariable String id, @RequestBody WishDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/user/{username}")
    public List<WishDTO> getAllByUser(@PathVariable String username) {
        return service.findAllByUsername(username);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<WishDTO> getById(@PathVariable String id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public WishDTO patch(@PathVariable String id, @RequestBody Map<String, Object> updates) throws JsonMappingException {
        return service.patch(id, updates);
    }

    /*@PostMapping("/search")
    public Page<Wish> search(@RequestBody WishSearchRequest request) {
        return service.search(request);
    }*/

    @PostMapping("/search")
    public PageResponse<Wish> search(@RequestBody WishSearchRequest request) {
        Page<Wish> page = service.search(request);
        return new PageResponse<>(page);
    }
}
