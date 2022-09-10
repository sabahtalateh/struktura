package ru.sabah.struktura;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;

@ApplicationScoped
@ApplicationPath("/api")
public class Application extends jakarta.ws.rs.core.Application {
}
