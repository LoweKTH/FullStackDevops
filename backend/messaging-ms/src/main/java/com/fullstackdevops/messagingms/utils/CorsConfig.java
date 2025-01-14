package com.fullstackdevops.messagingms.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from localhost:3000 (or your frontend's origin)
        registry.addMapping("/**") // Enable CORS for all endpoints
                .allowedOrigins("http://localhost:3000") // Origin of your frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow common methods
                .allowedHeaders("*") // Allow any headers
                .allowCredentials(true); // Allow credentials (cookies, authorization headers)
    }*/
}
