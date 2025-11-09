package com.dearlavion.coreservice.feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    List<FeedbackDTO> findAll();
    Optional<FeedbackDTO> findById(String id);
    FeedbackDTO create(FeedbackDTO dto);
    FeedbackDTO update(String id, FeedbackDTO dto);
    void delete(String id);
}
