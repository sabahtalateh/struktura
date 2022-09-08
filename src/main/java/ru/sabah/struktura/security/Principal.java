package ru.sabah.struktura.security;

public class Principal implements java.security.Principal {

    private final String name;

    public Principal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
