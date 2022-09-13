package ru.sabah.struktura.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void calcExpires() {
        var t = new Token();
        t.setExpiresAt(LocalDateTime.now().plusHours(1));
        var exp = t.calcExpires();
        assertEquals(3599, exp.getSeconds());
    }
}