package com.dearlavion.coreservice.request;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<RequestDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, RequestDTO.class));
    }

    @Override
    public List<RequestDTO> findAllByWishId(String wishId) {
        return repo.findAllByWishId(wishId)
                .stream()
                .map(e -> mapper.map(e, RequestDTO.class))
                .toList();
    }

    @Override
    public List<RequestDTO> findAllByUsername(String userId) {
        return repo.findAllByUsername(userId)
                .stream()
                .map(e -> mapper.map(e, RequestDTO.class))
                .toList();
    }

    @Override
    public RequestDTO create(RequestDTO dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            dto.setId(null);
        }

        Request entity = mapper.map(dto, Request.class);

        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(entity.getCreatedAt());

        Request saved = repo.save(entity);
        return mapper.map(saved, RequestDTO.class);
    }

    @Override
    public RequestDTO update(String id, RequestDTO dto) {
        return repo.findById(id).map(existing -> {

            mapper.map(dto, existing);
            existing.setUpdatedAt(new Date());

            //force replace portfolios instead of merging
            existing.setPortfolios(dto.getPortfolios());

            Request saved = repo.save(existing);
            return mapper.map(saved, RequestDTO.class);

        }).orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}