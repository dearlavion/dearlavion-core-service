package com.dearlavion.coreservice.wish;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private WishRepository repo;

    @Autowired
    private ModelMapper mapper;

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

        //to avoid duplicate categories
        entity.setCategories(dto.getCategories());

        // 🔥 Set createdAt only on creation
        entity.setCreatedAt(new Date());

        // 🔥 Optional: also set updatedAt = createdAt (good practice)
        entity.setUpdatedAt(entity.getCreatedAt());

        Wish saved = repo.save(entity);
        return mapper.map(saved, WishDTO.class);
    }

    @Override
    public WishDTO update(String id, WishDTO dto) {
        return repo.findById(id).map(existing -> {
            // Map incoming fields into existing entity
            mapper.map(dto, existing);

            // Ensure categories properly copied
            existing.setCategories(dto.getCategories());

            // 🔥 Update timestamp
            existing.setUpdatedAt(new Date());

            Wish saved = repo.save(existing);
            return mapper.map(saved, WishDTO.class);
        }).orElseThrow(() -> new RuntimeException("Wish not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<WishDTO> findAllByUserName(String userName) {
        return repo.findAllByUserName(userName)
                .stream()
                .map(e -> mapper.map(e, WishDTO.class))
                .toList();
    }
}
