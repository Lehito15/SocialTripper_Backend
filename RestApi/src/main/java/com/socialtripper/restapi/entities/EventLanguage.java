package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "events_languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventLanguage {
    public EventLanguage(Language language, BigDecimal requiredLevel) {
        this.requiredLevel = requiredLevel;
        this.language = language;
    }

    public EventLanguage(Event event, Language language, BigDecimal requiredLevel) {
        this.requiredLevel = requiredLevel;
        this.event = event;
        this.language = language;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
