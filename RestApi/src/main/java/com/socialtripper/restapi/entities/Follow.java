package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Encja reprezentująca obserwację konta innego użytkownika.
 * Klasa stanowi mapowanie tabeli follows.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link Account} - pole "follower"</li>
 *     <li>Relacja wiele do jednego z encją {@link Account} - pole "followed"</li>
 * </ul>
 */
@Entity
@Table(name = "follows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Follow {
    /**
     * Unikalny identyfikator obserwacji w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Data rozpoczęcia obserwowania.
     */
    @Column(name = "following_since")
    private LocalDate followingSince;

    /**
     * Data zakończenia obserwowania.
     * Ustawiona na null gdy obserwacja trwa.
     */
    @Column(name = "following_to")
    private LocalDate followingTo;

    /**
     * Użytkownik obserwujący.
     * Relacja wiele do jednego z tabelą accounts.
     */
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    @NotNull
    private Account follower;

    /**
     * Użytkownik obserwowany.
     * Relacja wiele do jednego z tablą accounts.
     */
    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    @NotNull
    private Account followed;


    /**
     * Konstruktor wiążący konto obserwującego i obserwowanego.
     *
     * @param follower użytkownik obserwujący
     * @param followed użytkownik obserwowany
     */
    public Follow(Account follower, Account followed) {
        this.follower = follower;
        this.followed = followed;
    }

    /**
     * Konstruktor wiążący konto obserwującego, konto obserwowanego i datę rozpoczęcia obserwacji.
     * Data końca obserwacji ustawiana na null.
     *
     * @param follower użytkownik obserwujący
     * @param followed użytkownik obserwowany
     * @param followingSince data rozpoczęcia obserwacji
     */
    public Follow(Account follower, Account followed, LocalDate followingSince) {
        this.follower = follower;
        this.followed = followed;
        this.followingSince = followingSince;
        this.followingTo = null;
    }
}
