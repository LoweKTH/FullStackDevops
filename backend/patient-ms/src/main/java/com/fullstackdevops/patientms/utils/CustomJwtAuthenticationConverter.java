package com.fullstackdevops.patientms.utils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<String>> {

    @Override
    public Collection<String> convert(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null || !resourceAccess.containsKey("user-ms")) {
            return Collections.emptyList();
        }

        Map<String, Object> userMs = (Map<String, Object>) resourceAccess.get("user-ms");
        List<String> roles = (List<String>) userMs.get("roles");

        // Prefix roles with "ROLE_" to align with Spring Security conventions
        return roles.stream()
                .map(role -> "ROLE_" + role.toUpperCase())
                .collect(Collectors.toList());
    }
}
