package com.dearlavion.coreservice.portfolio;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<PortfolioDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, PortfolioDTO.class));
    }

    @Override
    public Optional<PortfolioDTO> findByUsername(String username) {
        return repo.findByUsername(username).map(e -> mapper.map(e, PortfolioDTO.class));
    }

    @Override
    public PortfolioDTO create(PortfolioDTO dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            dto.setId(null);
        }

        Portfolio entity = mapper.map(dto, Portfolio.class);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(entity.getCreatedAt());

        Portfolio saved = repo.save(entity);
        return mapper.map(saved, PortfolioDTO.class);
    }

    @Override
    public PortfolioDTO update(String id, PortfolioDTO dto) {
        return repo.findById(id).map(existing -> {
            mapper.map(dto, existing);
            existing.setUpdatedAt(new Date());

            Portfolio saved = repo.save(existing);
            return mapper.map(saved, PortfolioDTO.class);
        }).orElseThrow(() -> new RuntimeException("Portfolio not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public List<PortfolioDTO> findAllByUsername(String username) {
        return repo.findAllByUsername(username)
                .stream()
                .map(e -> mapper.map(e, PortfolioDTO.class))
                .toList();
    }
}
