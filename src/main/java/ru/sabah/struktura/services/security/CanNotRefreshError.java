package ru.sabah.struktura.services.security;

import ru.sabah.struktura.services.ServiceError;

public class CanNotRefreshError extends ServiceError {
    public CanNotRefreshError() {
        super("can_not_refresh");
    }
}
