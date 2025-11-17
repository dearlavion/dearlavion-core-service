package com.dearlavion.coreservice.wish;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private WishRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<WishDTO> findAll() {
        return repo.findAll().stream().map(e -> mapper.map(e, WishDTO.class)).toList();
    }

    @Override
    public Optional<WishDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, WishDTO.class));
    }

    @Override
    public Optional<WishDTO> findByUserName(String userName) {
        return repo.findByUserName(userName).map(e -> mapper.map(e, WishDTO.class));
    }

    @Override
    public WishDTO create(WishDTO dto) {
        // Prevent empty string ID (Mongo will treat it as provided)
        if (dto.getId() == null || dto.getId().isBlank()) {
            dto.setId(null);
        }
        Wish entity = mapper.map(dto, Wish.class);
        Wish saved = repo.save(entity);
        return mapper.map(saved, WishDTO.class);
    }

    @Override
    public WishDTO update(String id, WishDTO dto) {
        return repo.findById(id).map(existing -> {
            String[] categories = dto.getCategories();
            mapper.map(dto, existing);
            existing.setCategories(categories); //avoid duplicate categories
            Wish saved = repo.save(existing);
            return mapper.map(saved, WishDTO.class);
        }).orElseThrow(() -> new RuntimeException("Wish not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
