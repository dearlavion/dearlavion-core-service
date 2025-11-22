package com.dearlavion.coreservice.wish;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends MongoRepository<Wish, String> {
    Optional<Wish> findByUsername(String username);
    List<Wish> findAllByUsername(String username);
}
