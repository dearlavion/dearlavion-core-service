package com.dearlavion.coreservice.wish;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishRepository extends MongoRepository<Wish, String> {
    Optional<Wish> findByUserName(String userName);
}
