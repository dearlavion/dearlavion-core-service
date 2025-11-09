package com.dearlavion.coreservice.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UserDTO> findAll() {
        return repo.findAll().stream().map(e -> mapper.map(e, UserDTO.class)).toList();
    }

    @Override
    public Optional<UserDTO> findById(String id) {
        return repo.findById(id).map(e -> mapper.map(e, UserDTO.class));
    }

    @Override
    public UserDTO create(UserDTO dto) {
        User entity = mapper.map(dto, User.class);
        User saved = repo.save(entity);
        return mapper.map(saved, UserDTO.class);
    }

    @Override
    public UserDTO update(String id, UserDTO dto) {
        return repo.findById(id).map(existing -> {
            mapper.map(dto, existing);
            existing.setId(id);
            User saved = repo.save(existing);
            return mapper.map(saved, UserDTO.class);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
