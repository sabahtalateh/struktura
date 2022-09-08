package ru.sabah.struktura.handlers;

import ru.sabah.struktura.handlers.utils.Json;

import javax.inject.Inject;

public abstract class BaseHandler {
    @Inject
    protected Json json;
}
