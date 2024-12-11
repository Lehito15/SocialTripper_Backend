package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

/**
 * Encja reprezentująca języki w systemie.
 * Klasa stanowi mapowanie tabeli languages.
 */
@Entity
@Table(name = "languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Language {
    /**
     * Unikalny identyfikator języka w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nazwa języka.
     */
    @Column(nullable = false, unique = true, length = 35)
    @NotNull
    private String name;
}
