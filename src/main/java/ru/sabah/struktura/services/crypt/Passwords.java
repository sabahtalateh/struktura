package ru.sabah.struktura.services.crypt;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Passwords {

    private static final int SALT_LEN = 32;
    private static final int HASH_LEN = 64;
    private static final int ITERATIONS = 5;
    private static final int MEMORY_KB = 65536;
    private static final int PARALLELISM = 5;

    Argon2 argon2;

    public Passwords() {
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, SALT_LEN, HASH_LEN);
    }

    public String hash(String password) {
        return argon2.hash(ITERATIONS, MEMORY_KB, PARALLELISM, password.toCharArray());
    }

    public boolean verify(String hash, String password) {
        return argon2.verify(hash, password.toCharArray());
    }
}
