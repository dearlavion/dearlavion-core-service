package com.dearlavion.coreservice.copilotrequest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopilotrequestRepository extends MongoRepository<Copilotrequest, String> {
}
