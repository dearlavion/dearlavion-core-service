package com.dearlavion.coreservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
    public NewTopic wishCreatedTopic() {
        return TopicBuilder.name("core-service-event")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
