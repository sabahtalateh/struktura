package ru.sabah.struktura.handlers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.time.format.DateTimeFormatter.*;

public class IsoOffset extends StdSerializer<Date> {

    // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_DATE_TIME
    private final DateTimeFormatter formatter = ISO_OFFSET_DATE_TIME;

    public IsoOffset() {
        this(null);
    }

    public IsoOffset(Class t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException {
        gen.writeString(formatter.format(value.toInstant().atZone(ZoneId.systemDefault())));
    }
}
