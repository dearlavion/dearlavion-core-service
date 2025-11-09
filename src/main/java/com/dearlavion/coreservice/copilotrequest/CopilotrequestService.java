package com.dearlavion.coreservice.copilotrequest;

import java.util.List;
import java.util.Optional;

public interface CopilotrequestService {
    List<CopilotrequestDTO> findAll();
    Optional<CopilotrequestDTO> findById(String id);
    CopilotrequestDTO create(CopilotrequestDTO dto);
    CopilotrequestDTO update(String id, CopilotrequestDTO dto);
    void delete(String id);
}
