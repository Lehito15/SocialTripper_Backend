package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "events_languages")
public class EventLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_language_id")
    private UUID id;

    @Column(name = "required_level", nullable = false, precision = 2, scale = 1)
    private BigDecimal requiredLevel;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull
    private Event event;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    @NotNull
    private Language language;
}
