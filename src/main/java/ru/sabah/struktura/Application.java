package ru.sabah.struktura;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;

@ApplicationScoped
@ApplicationPath("/api")
public class Application extends javax.ws.rs.core.Application {
}
