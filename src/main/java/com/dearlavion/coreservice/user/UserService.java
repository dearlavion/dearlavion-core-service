package com.dearlavion.coreservice.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();
    Optional<UserDTO> findById(String id);
    UserDTO create(UserDTO dto);
    UserDTO update(String id, UserDTO dto);
    void delete(String id);
}
