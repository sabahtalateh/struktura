package ru.sabah.struktura.handlers.filters;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import ru.sabah.struktura.security.SecurityContext;
import ru.sabah.struktura.services.security.Security;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    @ConfigProperty(name = "security.noAuthUrls")
    List<String> noAuth;

    @Inject
    private Security security;

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
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
        var user = security.findUserByAccess(bearer, new Date());
        if (user == null) {
            throw new NotAuthorizedException("Bearer");
        }

        SecurityContext sc = new SecurityContext(user);
        request.setSecurityContext(sc);
    }
}
