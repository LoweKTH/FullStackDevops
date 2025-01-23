package com.fullstackdevops.searchms.utils;


import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

@Provider
@Priority(1) // Ensure this filter runs early
public class LoggingFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(LoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        LOG.info("Incoming request: " + requestContext.getUriInfo().getRequestUri());

        // Log Authorization header
        String authHeader = requestContext.getHeaderString("Authorization");
        LOG.info("Authorization header: " + authHeader);

        // If JWT is available, log details
        if (jwt != null && jwt.getRawToken() != null) {
            LOG.info("JWT Token: " + jwt.getRawToken());
            LOG.info("JWT Issuer: " + jwt.getIssuer());
            LOG.info("JWT Subject: " + jwt.getSubject());
            LOG.info("JWT Claims: " + jwt.getClaimNames());
        } else {
            LOG.warn("JWT is null or invalid");
        }
    }
}
