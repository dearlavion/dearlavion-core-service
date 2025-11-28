package com.dearlavion.coreservice.config;

import com.dearlavion.coreservice.security.TokenVerificationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenVerificationFilter tokenVerificationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        //Public
                        .requestMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers(
                                "/api/wish/search",    //public wish endpoints
                                "/api/auth/**" )           //login/register
                        .permitAll()
                        // Everything else under /api/wish/** requires login
                       .requestMatchers("/api/wish/**").authenticated()
                        // Portfolio — matches Angular protected routes
                       .requestMatchers("/api/portfolio/**").authenticated()
                        // Request — matches Angular protected routes
                       .requestMatchers("/api/request/**").authenticated()
                        // Profile — matches Angular protected route
                       .requestMatchers("/api/user/profile/**").authenticated()
                        // Anything else allowed (optional)
                        .anyRequest().permitAll()
                )
                .addFilterBefore(tokenVerificationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Define your CORS rules here
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // your Angular app
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // needed if you send cookies or Authorization headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
