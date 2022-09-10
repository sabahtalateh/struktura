package ru.sabah.struktura.models;

import lombok.*;
import lombok.experimental.Accessors;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "registered_at", nullable = false)
    private Date registeredAt;
}
