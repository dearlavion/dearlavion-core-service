package com.dearlavion.coreservice.social;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialServiceImpl implements SocialService {
    @Autowired
    private SocialRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<SocialDTO> findAll() {
        return repo.findAll().stream().map(e -> mapper.map(e, SocialDTO.class)).toList();
    }

    @Override
    public Optional<SocialDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, SocialDTO.class));
    }

    @Override
    public SocialDTO create(SocialDTO dto) {
        Social entity = mapper.map(dto, Social.class);
        Social saved = repo.save(entity);
        return mapper.map(saved, SocialDTO.class);
    }

    @Override
    public SocialDTO update(String id, SocialDTO dto) {
        return repo.findById(id).map(existing -> {
            mapper.map(dto, existing);
            existing.setId(id);
            Social saved = repo.save(existing);
            return mapper.map(saved, SocialDTO.class);
        }).orElseThrow(() -> new RuntimeException("Social not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
