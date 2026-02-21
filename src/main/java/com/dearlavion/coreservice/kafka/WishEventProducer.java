package com.dearlavion.coreservice.kafka;

import com.dearlavion.coreservice.kafka.dto.WishEvent;
import com.dearlavion.coreservice.wish.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishWishCreated(Wish wish) {

        WishEvent event = WishEvent.builder()
                .id(wish.getId())
                .username(wish.getUsername())
                .title(wish.getTitle())
                .countryCode(wish.getCountryCode())
                .cityName(wish.getCityName())
                .amount(wish.getAmount())
                .build();

        kafkaTemplate.send("wish-event", event);
    }
}
