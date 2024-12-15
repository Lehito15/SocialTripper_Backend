package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Encja reprezentująca uczestników wydarzeń.
 * Klasa stanowi mapowanie tabeli events_participants
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link Event} - pole "event" </li>
 *     <li>Relacja wiele do jednego z encją {@link Account} - pole "participant"</li>
 * </ul>
 */
@Entity
@Table(name = "events_participants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventParticipant {
    /**
     * Unikalny identyfikator uczestnika wydarzenia w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_actual_participant")
    private boolean isActualParticipant;

    /**
     * Wydarzenie, któremu przypisany jest użytkownik.
     * Relacja wiele do jednego z tabelą events.
     */
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /**
     * Użytkownik będący uczestnikiem wydarzenia.
     * Relacja wiele do jednego z tabelą accounts.
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account participant;

    /**
     * Data dołączenie użytkownika do wydarzenia.
     */
    @Column(name = "joined_at", nullable = false)
    private LocalDate joinedAt;

    /**
     * Data opuszczenie wydarzenia przez użytkownika.
     * Do momentu opuszczenia ustawiona na null.
     */
    @Column(name = "left_at")
    private LocalDate leftAt;

    /**
     * Konstruktor wiążący uczestnika do wydarzenia.
     * Pomijany jest autoinkrementowany klucz główny.
     * Wartość daty opuszczenia ustawiana jest na null.
     *
     * @param event wydarzenie, do którego dołącza użytkownik
     * @param participant użytkownik dołączjący do wydarzenia
     * @param joinedAt data dołączenia użytkownika do wydarzenia.
     */
    public EventParticipant(Event event, Account participant, LocalDate joinedAt) {
        this.event = event;
        this.participant = participant;
        this.joinedAt = joinedAt;
        this.leftAt = null;
    }
}