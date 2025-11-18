package com.dearlavion.coreservice.portfolio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
    Optional<Portfolio> findByUserName(String userName);
    List<Portfolio> findAllByUserName(String userName);
}
