package ru.sabah.struktura.services;

public class ServiceError extends RuntimeException {
    public ServiceError(String message) {
        super(message);
    }
}
