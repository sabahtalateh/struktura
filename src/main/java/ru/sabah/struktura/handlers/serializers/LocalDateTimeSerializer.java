package ru.sabah.struktura.handlers.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();

    private LocalDateTimeSerializer() {
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ZonedDateTime ldtZoned = value.atZone(ZoneId.systemDefault());
        ZonedDateTime utc = ldtZoned.withZoneSameInstant(ZoneId.of("+00:00"));
        gen.writeString(utc.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
