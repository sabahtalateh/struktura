package ru.sabah.struktura.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.eclipse.microprofile.graphql.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "registered_at")
    private Date registeredAt;
}
