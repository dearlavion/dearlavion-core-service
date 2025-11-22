package com.dearlavion.coreservice.portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    List<PortfolioDTO> findAllByUsername(String username);
    Optional<PortfolioDTO> findById(String id);
    Optional<PortfolioDTO> findByUsername(String username);
    PortfolioDTO create(PortfolioDTO dto);
    PortfolioDTO update(String id, PortfolioDTO dto);
    void delete(String id);
}
