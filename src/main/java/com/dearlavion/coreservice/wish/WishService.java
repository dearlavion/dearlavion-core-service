package com.dearlavion.coreservice.wish;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WishService {
    List<WishDTO> findAllByUsername(String username);
    Optional<WishDTO> findById(String id);
    Optional<WishDTO> findByUsername(String username);
    WishDTO create(WishDTO dto);
    WishDTO update(String id, WishDTO dto);
    void delete(String id);
    WishDTO patch(String id, Map<String, Object> updates) throws JsonMappingException;
}
