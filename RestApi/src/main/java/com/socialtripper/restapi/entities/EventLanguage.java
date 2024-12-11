package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * Encja reprezentująca języki przypisane do wydarzenia.
 * Klasa stanowi mapowanie tabeli events_languages.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link Event} - pole "event"</li>
 *     <li>Relacja wiele do jednego z encją {@link Language} - pole "language"</li>
 * </ul>
 */
@Entity
@Table(name = "events_languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventLanguage {
    /**
     * Unikalny identyfikator języku wydarzenia w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Wymagany poziom w danym języku dla uczestników wydarzenia.
     * Skala od 0 do 10.
     */
    @Column(name = "required_level", nullable = false, precision = 3, scale = 1)
    private BigDecimal requiredLevel;

    /**
     * Wydarzenie, do którego przypisano język.
     * Relacja wiele do jednego z tabelą events.
     */
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /**
     * Język, który przypisano do wydarzenia.
     * Relacja wiele do jednego z tabelą languages.
     */
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    /**
     * Konstruktor z informacjami dotyczącymi języka.
     *
     * @param language przypisywany język
     * @param requiredLevel poziom dla danego języka
     */
    public EventLanguage(Language language, BigDecimal requiredLevel) {
        this.requiredLevel = requiredLevel;
        this.language = language;
    }

    /**
     * Pełen konstruktor, pomijaący autoinkrementowany klucz.
     *
     * @param event wydarzenie, któremu przypisywany jest język
     * @param language przypisywany wydarzeniu język
     * @param requiredLevel wymagany dla uczestników poziom językowy
     */
    public EventLanguage(Event event, Language language, BigDecimal requiredLevel) {
        this.requiredLevel = requiredLevel;
        this.event = event;
        this.language = language;
    }
}
