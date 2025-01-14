package com.fullstackdevops.patientms.utils;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Map;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .cors(Customizer.withDefaults()) // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/patients/**").authenticated() // Secure all endpoints under /api/patients
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(customJwtDecoder()))); // Use custom JWT decoder

        return http.build();
    }

    @Bean
    public JwtDecoder customJwtDecoder() {
        // JWK Set URL for Keycloak or your desired URL
        String jwkSetUri = "http://keycloak:8080/realms/PatientSystem/protocol/openid-connect/certs";

        // Configure the NimbusJwtDecoder with the JWK Set URI (or URL of your choice)
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        // Add any custom logic to modify JWT claims (e.g., issuer claim)
        return new JwtDecoder() {
            @Override
            public Jwt decode(String token) throws JwtException {
                // Decode the JWT token using NimbusJwtDecoder first
                Jwt jwt = jwtDecoder.decode(token);

                // Custom logic for JWT validation or claims modification (e.g., checking the issuer)
                if ("http://localhost:8080".equals(jwt.getIssuer())) {
                    // Modify claims or handle issuer mismatch logic
                    Map<String, Object> claims = jwt.getClaims();
                    claims.put("iss", "http://keycloak:8080/realms/PatientSystem");

                    // Return a new JWT with modified claims
                    return Jwt.withTokenValue(token)
                            .header("alg", "RS256")
                            .claims(claimsMap -> claimsMap.putAll(claims))
                            .build();
                }

                return jwt;
            }
        };
    }

    /*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Add your frontend URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
}
