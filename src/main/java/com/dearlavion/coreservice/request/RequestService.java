package com.dearlavion.coreservice.request;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RequestService {

    Optional<RequestDTO> findById(String id);
    List<RequestDTO> findAllByWishId(String wishId);
    List<RequestDTO> findAllByUsername(String userId);
    RequestDTO create(RequestDTO dto);
    RequestDTO update(String id, RequestDTO dto);
    void delete(String id);
    RequestDTO patch(String id, Map<String, Object> updates) throws JsonMappingException;
}
