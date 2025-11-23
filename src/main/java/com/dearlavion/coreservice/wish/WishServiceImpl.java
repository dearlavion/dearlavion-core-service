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

        // 1️⃣ Extract request append operation
        Object requestIdObj = updates.remove("requestId");
        String requestId = requestIdObj != null ? requestIdObj.toString() : null;

        Object copilotNameObj = updates.remove("copilotName");
        String copilotName = copilotNameObj != null ? copilotNameObj.toString() : null;

        // 2️⃣ Merge scalar fields automatically into Wish
        objectMapper.updateValue(wish, updates);

        // 3️⃣ Append a new WishRequestDTO if requestId or copilotName exists
        if (!copilotName.isBlank() && !requestId.isBlank()) {

            // Initialize list if null
            if (wish.getWishRequestList() == null) {
                wish.setWishRequestList(new ArrayList<>());
            }

            // Prevent duplicate copilotName (industry best practice)
            boolean alreadyExists = wish.getWishRequestList().stream()
                    .anyMatch(wr -> copilotName != null && copilotName.equals(wr.getCopilotName()));

            if (!alreadyExists) {
                WishRequestDTO wr = new WishRequestDTO();
                wr.setRequestId(requestId);
                wr.setCopilotName(copilotName);

                wish.getWishRequestList().add(wr);
            }
        }

        // 4️⃣ Update timestamp
        wish.setUpdatedAt(new Date());

        Wish saved = repo.save(wish);

        return mapper.map(saved, WishDTO.class);
    }



}
