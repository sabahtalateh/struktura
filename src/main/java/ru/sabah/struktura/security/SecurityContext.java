package ru.sabah.struktura.security;

import ru.sabah.struktura.models.Token;
import ru.sabah.struktura.models.User;

import java.security.Principal;

public class SecurityContext implements jakarta.ws.rs.core.SecurityContext {

    private final ru.sabah.struktura.security.Principal principal;

    public SecurityContext(User user) {
        principal = new ru.sabah.struktura.security.Principal(user.getUsername());
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
