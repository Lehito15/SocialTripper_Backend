package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Encja reprezentująca członków grup.
 * Klasa stanowi mapowanie tabeli groups_participants.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link Group} - pole "group"</li>
 *     <li>Relacja wiele do jednego z encją {@link Account} - pole "participant"</li>
 * </ul>
 */
@Entity
@Table(name = "groups_participants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupParticipant {
    /**
     * Unikalny identifikaotr członka grupy w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Grupa, do której przypisany jest użytkownik.
     * Relacja wiele do jednego z tabelą groups.
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * Użytkownik przypisany do grupy.
     * Relacja wiele do jednego z tabelą accounts.
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account participant;

    /**
     * Data dołączenia do grupy.
     */
    @Column(name = "joined_at")
    private LocalDate joinedAt;

    /**
     * Data opuszczenia grupy.
     * Gdy użytkownik jest członkiem grupy wartość ustawiona na null.
     */
    @Column(name = "left_at")
    private LocalDate leftAt;

    /**
     * Konstruktor wiążący użytkownika z grupą i datą dołączenia.
     *
     *
     * @param group grupa, której użytkownik jest członkiem
     * @param participant użytkownik będący członkiem grupy
     * @param joinedAt data dołączenia do grupy
     */
    public GroupParticipant(Group group, Account participant,
                            LocalDate joinedAt) {
        this.group = group;
        this.participant = participant;
        this.joinedAt = joinedAt;
    }
}
