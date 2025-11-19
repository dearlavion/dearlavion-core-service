package com.dearlavion.coreservice.request;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    Optional<RequestDTO> findById(String id);
    List<RequestDTO> findAllByWishId(String wishId);
    List<RequestDTO> findAllByUserId(String userId);

    RequestDTO create(RequestDTO dto);
    RequestDTO update(String id, RequestDTO dto);
    void delete(String id);
}
