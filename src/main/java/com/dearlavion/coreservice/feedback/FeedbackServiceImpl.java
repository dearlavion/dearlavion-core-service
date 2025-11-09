package com.dearlavion.coreservice.feedback;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<FeedbackDTO> findAll() {
        return repo.findAll().stream().map(e -> mapper.map(e, FeedbackDTO.class)).toList();
    }

    @Override
    public Optional<FeedbackDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, FeedbackDTO.class));
    }

    @Override
    public FeedbackDTO create(FeedbackDTO dto) {
        Feedback entity = mapper.map(dto, Feedback.class);
        Feedback saved = repo.save(entity);
        return mapper.map(saved, FeedbackDTO.class);
    }

    @Override
    public FeedbackDTO update(String id, FeedbackDTO dto) {
        return repo.findById(id).map(existing -> {
            mapper.map(dto, existing);
            existing.setId(id);
            Feedback saved = repo.save(existing);
            return mapper.map(saved, FeedbackDTO.class);
        }).orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
