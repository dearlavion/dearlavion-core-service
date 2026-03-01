package com.dearlavion.coreservice.kafka;

import com.dearlavion.coreservice.kafka.dto.CoreServiceEvent;
import com.dearlavion.coreservice.kafka.dto.EventType;
import com.dearlavion.coreservice.kafka.dto.WishEvent;
import com.dearlavion.coreservice.wish.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishWishCreated(Wish wish) {

        WishEvent wishEvent = WishEvent.builder()
                .id(wish.getId())
                .username(wish.getUsername())
                .title(wish.getTitle())
                .countryCode(wish.getCountryCode())
                .cityName(wish.getCityName())
                .amount(wish.getAmount())
                .build();

        CoreServiceEvent event = CoreServiceEvent.builder()
                .type(EventType.NEW_WISH)
                .payload(wishEvent)
                .build();

        kafkaTemplate.send("core-service-event", event);
    }
}
