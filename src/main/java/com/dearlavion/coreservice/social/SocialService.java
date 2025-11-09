package com.dearlavion.coreservice.social;

import java.util.List;
import java.util.Optional;

public interface SocialService {
    List<SocialDTO> findAll();
    Optional<SocialDTO> findById(String id);
    SocialDTO create(SocialDTO dto);
    SocialDTO update(String id, SocialDTO dto);
    void delete(String id);
}
