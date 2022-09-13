package ru.sabah.struktura.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime refreshExpiresAt;

    public Duration calcExpires() {
        var diff = ChronoUnit.SECONDS.between(LocalDateTime.now(), this.getExpiresAt());
        if (diff < 0) {
            diff = 0;
        }
        return Duration.ofSeconds(diff);
    }
}
