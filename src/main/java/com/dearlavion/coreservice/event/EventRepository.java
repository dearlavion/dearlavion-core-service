package com.dearlavion.coreservice.event;

import com.dearlavion.coreservice.event.search.EventCustomRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String>, EventCustomRepository {
    List<Event> findAllByUsername(String username);
}

