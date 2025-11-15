package com.dearlavion.coreservice.security;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class AuthServiceClient {

    private final WebClient webClient;

    public AuthServiceClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://dearlavion-authentication-service/api/auth")
                .build();
    }

    public AuthVerificationResponse verify(String token) {
        return webClient.post()
                .uri("/verify")
                .bodyValue(Map.of("token", token))
                .retrieve()
                .bodyToMono(AuthVerificationResponse.class)
                .block();
    }
}

