package ru.sabah.struktura.services.security;

import ru.sabah.struktura.services.ServiceError;

public class WeakPasswordError extends ServiceError {
    public WeakPasswordError() {
        super("weak_password");
    }
}
