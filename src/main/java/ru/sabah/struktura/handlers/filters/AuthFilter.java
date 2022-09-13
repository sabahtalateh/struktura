package ru.sabah.struktura.handlers.filters;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import ru.sabah.struktura.security.SecurityContext;
import ru.sabah.struktura.services.security.Security;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

    List<String> noAuth = new ArrayList<>(Arrays.asList(
            "/api/v1/register",
            "/api/v1/login",
            "/api/swagger"
    ));

    @Inject
    private Security security;

    @Override
    public void filter(ContainerRequestContext request) throws NotAuthorizedException {
        var path = request.getUriInfo().getAbsolutePath().getPath();
        if (noAuth.contains(path)) {
            return;
        }

        String authHeader = request.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            throw new NotAuthorizedException("Bearer");
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Bearer");
        }

        var bearer = authHeader.substring("Bearer ".length()).trim();
        var user = security.findUserByAccess(bearer, LocalDateTime.now());
        if (user == null) {
            throw new NotAuthorizedException("Bearer");
        }

        SecurityContext sc = new SecurityContext(user);
        request.setSecurityContext(sc);
    }
}
