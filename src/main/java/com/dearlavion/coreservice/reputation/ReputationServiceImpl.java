package com.dearlavion.coreservice.reputation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReputationServiceImpl implements ReputationService {
    @Autowired
    private ReputationRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ReputationDTO> findAll() {
        return repo.findAll().stream().map(e -> mapper.map(e, ReputationDTO.class)).toList();
    }

    @Override
    public Optional<ReputationDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, ReputationDTO.class));
    }

    @Override
    public ReputationDTO create(ReputationDTO dto) {
        Reputation entity = mapper.map(dto, Reputation.class);
        Reputation saved = repo.save(entity);
        return mapper.map(saved, ReputationDTO.class);
    }

    @Override
    public ReputationDTO update(String id, ReputationDTO dto) {
        return repo.findById(id).map(existing -> {
            mapper.map(dto, existing);
            existing.setId(id);
            Reputation saved = repo.save(existing);
            return mapper.map(saved, ReputationDTO.class);
        }).orElseThrow(() -> new RuntimeException("Reputation not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
