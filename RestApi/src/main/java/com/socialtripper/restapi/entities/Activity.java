package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

/**
 * Encja reprezentująca typ aktywności.
 * Klasa stanowi mapowanie na tabelę activities w bazie danych.
 */
@Entity
@Table(name = "activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Activity {
    /**
     * Unikalny identyfikator aktywności w tabeli.
     */
    @Id
    private Long id;

    /**
     * Nazwa aktywności.
     * Przyjmuje wartości:
     * <ul>
     *     <li>water</li>
     *     <li>walking</li>
     *     <li>sport</li>
     *     <li>running</li>
     *     <li>camping</li>
     *     <li>ride</li>
     *     <li>hiking</li>
     * </ul>
     */
    @Column(nullable = false, unique = true, length = 100)
    @NotNull
    private String name;
}
