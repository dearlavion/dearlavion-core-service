package com.dearlavion.coreservice.security;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String authServiceUrl = "http://localhost:8080/api/auth/verify";

    public AuthVerificationResponse verify(String token) {
        try {
            return restTemplate.postForObject(
                    authServiceUrl,
                    Map.of("token", token),
                    AuthVerificationResponse.class
            );
        } catch (Exception e) {
            return null; // token invalid or service unavailable
        }
    }
}