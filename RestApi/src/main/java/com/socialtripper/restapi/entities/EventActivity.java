package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Encja reprezentująca aktywności przypisane do wydarzenia.
 * Klasa stanowi mapowanie tabeli users_activities.
 */
@Entity
@Table(name = "events_activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventActivity {
    /**
     * Unikalny identyfikator aktywności wydarzenia w tabeli.
     * Autoinkrementowany klucz główny.
     * <b>Relacje: </b>
     * <ul>
     *     <li>Relacja wiele do jednego z encją {@link Event} - pole "event"</li>
     *     <li>Relacja wiele do jednoego z encją {@link Activity} 0 pole "activity"</li>
     * </ul>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Wymagane dla użytkownika doświadczenie w aktywności.
     * Oceniane w skali od 0 do 10.
     */
    @Column(name = "required_experience", nullable = false, precision = 3, scale = 1)
    private BigDecimal requiredExperience;

    /**
     * Wydarzenie, do którego przypisana jest aktywność.
     * Relacja wiele do jedneog z tabelą events.
     */
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /**
     * Aktywność przypisana do wydarzenia.
     * Relacja wiele do jednego z tabelą activities.
     */
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    /**
     * Pełen konstruktor, pomijający autoinkrementowany klucz główny.
     *
     * @param event wydarzenie, któremu przypisywana jest aktywność
     * @param activity aktywność przypisywana wydarzeniu
     * @param requiredExperience wymagane dla użytkownika doświadczenie w aktywności
     */
    public EventActivity(Event event, Activity activity, BigDecimal requiredExperience) {
        this.requiredExperience = requiredExperience;
        this.event = event;
        this.activity = activity;
    }

    /**
     * Konstruktor z informacjami dotyczącymi aktywności.
     *
     * @param activity aktywność przypisywana wydarzeniu
     * @param requiredExperience wymagane dla użytkownika doświadczenie w aktywności
     */
    public EventActivity(Activity activity, BigDecimal requiredExperience) {
        this.requiredExperience = requiredExperience;
        this.activity = activity;
    }
}