package com.fullstackdevops.patientms.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/api/**")) // Ignore CSRF for /api/**
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/**").permitAll() // Allow all requests to /api/**
                        .anyRequest().authenticated()); // Authenticate other requests
        return http.build();
    }

}
