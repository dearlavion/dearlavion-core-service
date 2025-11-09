package com.dearlavion.coreservice.wish;

import java.util.List;
import java.util.Optional;

public interface WishService {
    List<WishDTO> findAll();
    Optional<WishDTO> findById(String id);
    WishDTO create(WishDTO dto);
    WishDTO update(String id, WishDTO dto);
    void delete(String id);
}
