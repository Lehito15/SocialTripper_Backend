package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "users_languages")
public class UserLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 2, scale = 1)
    @NotNull
    private BigDecimal level;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    @NotNull
    private Language language;
}
