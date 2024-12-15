package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Encja reprezentująca języki użytkownika.
 * Klasa mapuje tabelę users_languages.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link User} - pole "user"</li>
 *     <li>Relacja wiele do jednego z encją {@link Language} - pole "language"</li>
 * </ul>
 */
@Entity
@Table(name = "users_languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLanguage {
    /**
     * Unikalny identyfikator języka użytkownika w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Poziom znajomości języka.
     * Skala od 0 do 10.
     */
    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal level;

    /**
     * Użytkownik z przypidanym językiem.
     * Relacja wiele do jednego z tabelą users.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Język przpisany do użtykownika.
     * Relacja wiele do jednego z tabelą languages.
     */
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    /**
     * Konstruktor wiążący użytkownika z językiem i poziomem jego znajomości.
     *
     *
     * @param level poziom znajomości języka
     * @param user użytkowni z przpisanym językiem
     * @param language język przypisany do użytkownika
     */
    public UserLanguage(BigDecimal level, User user, Language language) {
        this.level = level;
        this.user = user;
        this.language = language;
    }

    /**
     * Konstruktor wiążący język z poziomem jego znajomości.
     *
     * @param level poziom znajomości języka
     * @param language język przypisany do użytkownika
     */
    public UserLanguage(BigDecimal level, Language language) {
        this.level = level;
        this.language = language;
    }
}
