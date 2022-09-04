package ru.sabah.struktura.services.accounts;

import ru.sabah.struktura.services.ServiceException;

public class EmailOccupiedException extends ServiceException {
    public EmailOccupiedException() {
        super("email_occupied");
    }
}
