package com.dearlavion.coreservice.reputation;

import java.util.List;
import java.util.Optional;

public interface ReputationService {
    List<ReputationDTO> findAll();
    Optional<ReputationDTO> findById(String id);
    ReputationDTO create(ReputationDTO dto);
    ReputationDTO update(String id, ReputationDTO dto);
    void delete(String id);
}
