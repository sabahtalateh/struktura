package ru.sabah.struktura.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String access;

    @Column(nullable = false)
    private String refresh;

    @Column(nullable = false)
    private Date expiresAt;

    @Column(nullable = false)
    private Date refreshExpiresAt;

    public Duration calcExpires() {
        return Duration.ofMillis(this.getExpiresAt().getTime() - new Date().getTime());
    }
}
