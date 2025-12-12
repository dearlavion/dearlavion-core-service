package com.dearlavion.coreservice.request;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends MongoRepository<Request, String> {
    List<Request> findByUsernameAndRequestType(String username, String requestType);
}
