package com.dearlavion.coreservice.wish;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends MongoRepository<Wish, String> {
}
