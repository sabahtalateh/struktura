package ru.sabah.struktura.handlers.serializers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;

public class LocalDateTimeModule extends SimpleModule {
    public LocalDateTimeModule() {
        addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
    }
}
