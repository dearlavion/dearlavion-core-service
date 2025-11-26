package com.dearlavion.coreservice.request;

import com.dearlavion.coreservice.wish.WishDTO;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    private RequestService service;

    @PostMapping
    public RequestDTO create(@RequestBody RequestDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RequestDTO update(@PathVariable String id, @RequestBody RequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RequestDTO> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/wish/{wishId}")
    public List<RequestDTO> getAllByWish(@PathVariable String wishId) {
        return service.findAllByWishId(wishId);
    }

    @GetMapping("/user/{userId}")
    public List<RequestDTO> getAllByUser(@PathVariable String userId) {
        return service.findAllByUsername(userId);
    }

    @PatchMapping("/{id}")
    public RequestDTO patch(@PathVariable String id, @RequestBody Map<String, Object> updates) throws JsonMappingException {
        return service.patch(id, updates);
    }
}
