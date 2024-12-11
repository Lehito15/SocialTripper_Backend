package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Encja reprezentująca aktywności użytkownika.
 * Klasa stanowi mapowanie tabeli users_activities.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link User} - pole "user"</li>
 *     <li>Relacja wiele do jednego z encją {@link Activity} - pole "activity"</li>
 * </ul>
 */
@Entity
@Table(name = "users_activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserActivity {
    /**
     * Unikalny identyfikator aktywności użytkownika.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Doświadczenie użytkownika w dyscyplinie.
     * Skala od 0 do 10.
     */
    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal experience;

    /**
     * Użytkownik z przypisaną aktywnością.
     * Relacja wiele do jednego z tabelą users.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Aktywność przypisana do użytkownika.
     * Relacja wiele do jednego z tabelą activities.
     */
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    /**
     * Konstruktor wiążący użytkownika z aktywnością i poziomiem doświadczenia.
     *
     * @param experience poziom doświadczenia
     * @param user użtykownik z przypisaną aktywnością
     * @param activity aktywność przypisana do użytkownika
     */
    public UserActivity(BigDecimal experience, User user, Activity activity) {
        this.experience = experience;
        this.user = user;
        this.activity = activity;
    }

    /**
     * Konstruktor wiążący aktyność i poziom doświadzenia.
     *
     * @param experience poziom doświadczenia
     * @param activity aktywność przypisana do użytkownika
     */
    public UserActivity(BigDecimal experience, Activity activity) {
        this.experience = experience;
        this.activity = activity;
    }
}
