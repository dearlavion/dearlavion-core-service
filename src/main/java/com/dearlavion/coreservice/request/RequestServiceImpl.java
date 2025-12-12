package com.dearlavion.coreservice.request;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Optional<RequestDTO> findById(String id) {
        return repository.findById(id).map(e -> mapper.map(e, RequestDTO.class));
    }

    @Override
    public RequestDTO create(RequestDTO dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            dto.setId(null);
        }

        Request entity = mapper.map(dto, Request.class);

        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(entity.getCreatedAt());

        Request saved = repository.save(entity);
        return mapper.map(saved, RequestDTO.class);
    }

    @Override
    public RequestDTO update(String id, RequestDTO dto) {
        return repository.findById(id).map(existing -> {

            mapper.map(dto, existing);
            existing.setUpdatedAt(new Date());

            //force replace portfolios instead of merging
            existing.setPortfolios(dto.getPortfolios());

            Request saved = repository.save(existing);
            return mapper.map(saved, RequestDTO.class);

        }).orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public RequestDTO patch(String id, Map<String, Object> updates) throws JsonMappingException {
        Request request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Object statusObj = updates.remove("status");
        String status = statusObj != null ? statusObj.toString() : null;

        // Merge other scalar fields
        objectMapper.updateValue(request, updates);
        if (!status.isBlank()) {
            request.setStatus(status);
        }
        // Update timestamp
        request.setUpdatedAt(new Date());
        Request saved = repository.save(request);

        return mapper.map(saved, RequestDTO.class);
    }

    @Override
    public List<RequestDTO> findUserRequests(String username, String type) {
        List<Request> requests = repository.findByUsernameAndRequestType(username, type);

        // Convert to DTO
        return requests
            .stream()
            .map(e -> mapper.map(e, RequestDTO.class))
            .toList();
    }

}