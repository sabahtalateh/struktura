package ru.sabah.struktura.services.accounts;

import ru.sabah.struktura.services.ServiceException;

public class WeakPasswordException extends ServiceException {
    public WeakPasswordException() {
        super("weak_password");
    }
}
