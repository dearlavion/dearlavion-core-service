package com.dearlavion.coreservice.wish;

import java.util.List;
import java.util.Optional;

public interface WishService {
    List<WishDTO> findAllByUsername(String username);
    Optional<WishDTO> findById(String id);
    Optional<WishDTO> findByUsername(String username);
    WishDTO create(WishDTO dto);
    WishDTO update(String id, WishDTO dto);
    void delete(String id);
}
