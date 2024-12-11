package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Encja reprezentująca zasięg grupy.
 * Klasa stanowi mapowanie tabeli locations_scopes.
 */
@Entity
@Table(name = "locations_scopes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationScope {
    /**
     * Unikalny identyfikator zasięgu grupy w tabeli.
     */
    @Id
    private Long id;

    /**
     * Nazwa typu zasięgu grupy.
     */
    @Column(nullable = false, unique = true, length = 30)
    private String name;
}
