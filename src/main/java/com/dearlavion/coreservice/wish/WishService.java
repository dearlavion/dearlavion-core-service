package com.dearlavion.coreservice.wish;

import java.util.List;
import java.util.Optional;

public interface WishService {
    List<WishDTO> findAll();
    Optional<WishDTO> findById(String id);
    Optional<WishDTO> findByUserName(String userName);
    WishDTO create(WishDTO dto);
    WishDTO update(String id, WishDTO dto);
    void delete(String id);
}
