package com.dearlavion.coreservice.portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    List<PortfolioDTO> findAllByUserName(String userName);
    Optional<PortfolioDTO> findById(String id);
    Optional<PortfolioDTO> findByUserName(String userName);
    PortfolioDTO create(PortfolioDTO dto);
    PortfolioDTO update(String id, PortfolioDTO dto);
    void delete(String id);
}
