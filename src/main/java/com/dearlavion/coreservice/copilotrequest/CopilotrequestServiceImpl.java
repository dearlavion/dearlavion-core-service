package com.dearlavion.coreservice.copilotrequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopilotrequestServiceImpl implements CopilotrequestService {
    @Autowired
    private CopilotrequestRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CopilotrequestDTO> findAll() {
        return repo.findAll().stream().map(e -> mapper.map(e, CopilotrequestDTO.class)).toList();
    }

    @Override
    public Optional<CopilotrequestDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, CopilotrequestDTO.class));
    }

    @Override
    public CopilotrequestDTO create(CopilotrequestDTO dto) {
        Copilotrequest entity = mapper.map(dto, Copilotrequest.class);
        Copilotrequest saved = repo.save(entity);
        return mapper.map(saved, CopilotrequestDTO.class);
    }

    @Override
    public CopilotrequestDTO update(String id, CopilotrequestDTO dto) {
        return repo.findById(id).map(existing -> {
            mapper.map(dto, existing);
            existing.setId(id);
            Copilotrequest saved = repo.save(existing);
            return mapper.map(saved, CopilotrequestDTO.class);
        }).orElseThrow(() -> new RuntimeException("Copilotrequest not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
