package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Encja reprezentująca statusy wydarzeń w systemie.
 * Klasa stanowi mapowanie tabeli events_statuses.
 */
@Entity
@Table(name = "events_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventStatus {
    /**
     * Unikalny identyfikator statusu wydarzenia w systemie.
     */
    @Id
    private Long id;

    /**
     * Nazwa statusu wydarzenia.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
