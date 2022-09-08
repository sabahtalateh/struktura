package ru.sabah.struktura.services.security;

import ru.sabah.struktura.services.ServiceError;

public class CanNotLoginError extends ServiceError {
    public CanNotLoginError() {
        super("can_not_login");
    }
}
