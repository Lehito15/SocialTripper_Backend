package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Encja reprezentująca rekomendacje użytkowników.
 * Klasa mapuje tabelę users_recommendations.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link User} - pole "user"</li>
 *     <li>Relacja wiele do jednego z encją {@link User} - pole "recommendedUser"</li>
 * </ul>
 */
@Entity
@Table(name = "users_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecommendation {
    /**
     * Unikalny identyfikator rekomendowanego użytkownika w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Użytkownik, któremu rekomendowani są inni użytkownicy.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Użytkownik rekomendowany innemu użytkownikowi.
     */
    @ManyToOne
    @JoinColumn(name = "recommended_user_id")
    private User recommendedUser;
}
