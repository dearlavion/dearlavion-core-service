package com.dearlavion.coreservice.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService service;

    @PostMapping
    public PortfolioDTO create(@RequestBody PortfolioDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PortfolioDTO update(@PathVariable String id, @RequestBody PortfolioDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{username}")
    public List<PortfolioDTO> getAllByUser(@PathVariable String username) {
        return service.findAllByUsername(username);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PortfolioDTO> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
