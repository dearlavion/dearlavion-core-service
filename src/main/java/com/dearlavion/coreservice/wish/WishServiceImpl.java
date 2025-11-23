package com.dearlavion.coreservice.wish;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private WishRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Optional<WishDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, WishDTO.class));
    }

    @Override
    public Optional<WishDTO> findByUsername(String username) {
        return repo.findByUsername(username).map(e -> mapper.map(e, WishDTO.class));
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
    public List<WishDTO> findAllByUsername(String username) {
        return repo.findAllByUsername(username)
                .stream()
                .map(e -> mapper.map(e, WishDTO.class))
                .toList();
    }


    @Override
    public WishDTO patch(String id, Map<String, Object> updates) throws JsonMappingException {
        Wish wish = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wish not found"));

        // 1️⃣ Extract append operation
        Object requestsAppend = updates.remove("requestId");

        // 2️⃣ Merge scalar fields using ObjectMapper
        objectMapper.updateValue(wish, updates);

        // 3️⃣ Handle array append safely
        if (requestsAppend instanceof String requestId) {
            // Convert array -> list
            List<String> list = wish.getRequests() == null
                    ? new ArrayList<>()
                    : new ArrayList<>(Arrays.asList(wish.getRequests()));

            // Add only if not duplicate
            if (!list.contains(requestId)) {
                list.add(requestId);
            }

            // Set back as array
            wish.setRequests(list.toArray(new String[0]));
        }

        // 4️⃣ Update timestamp
        wish.setUpdatedAt(new Date());

        Wish saved = repo.save(wish);

        return mapper.map(saved, WishDTO.class);
    }



}
