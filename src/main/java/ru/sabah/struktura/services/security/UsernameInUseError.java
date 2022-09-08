package ru.sabah.struktura.services.security;

import ru.sabah.struktura.services.ServiceError;

public class UsernameInUseError extends ServiceError {
    public UsernameInUseError() {
        super("username_in_use");
    }
}
