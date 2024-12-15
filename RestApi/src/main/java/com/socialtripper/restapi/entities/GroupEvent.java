package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Encja reprezentująca grupowe wydarzenia.
 * Klasa stanowi mapowanie tabeli groups_events.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link Event} - pole "event"</li>
 *     <li>Relacja wiele od jednego z encją {@link Group} - pole "group"</li>
 * </ul>
 */
@Entity
@Table(name = "groups_events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupEvent {
    /**
     * Unikalny identyfikator wydarzenia.
     * Identyczny z identyfikatorem z tabeli events.
     */
    @Id
    private Long id;

    /**
     * Wydarzenie przypisane do grupy.
     * Relacja wiele do jednego z tabelą events.
     */
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @MapsId
    private Event event;

    /**
     * Grupa z przypisanym wydarzeniem.
     * Relacja wiele do jednego z tabelą groups.
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * Konstruktor wiążący wydarzenie z grupą.
     *
     *
     * @param event wydarzenie przypisane do grupy
     * @param group grupa z przypisanym wydarzeniem
     */
    public GroupEvent(Event event, Group group) {
        this.event = event;
        this.group = group;
    }

    /**
     * Konsturktor wiążący wydarzenie.
     *
     * @param event wydarzenie przypisywane do grupy
     */
    public GroupEvent(Event event) {
        this.event = event;
    }
}
