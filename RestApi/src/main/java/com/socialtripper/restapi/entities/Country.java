package com.socialtripper.restapi.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Encja reprezentująca kraj .
 * Klasa stanowi mapowanie na tabelę countries w bazie danych.
 */
@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Country {
    /**
     * Unikalny identyfikator kraju w tabeli.
     * Automatycznie inkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nazwa kraju.
     */
    @Column(nullable = false, unique = true, length = 50)
    @NotNull
    private String name;

    /**
     * Konstruktor tworzący encję na podstawie nazwy kraju.
     *
     * @param name nazwa kraju
     */
    public Country(String name) {
        this.name = name;
    }
}
