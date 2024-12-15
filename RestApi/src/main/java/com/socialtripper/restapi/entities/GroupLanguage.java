package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

/**
 * Encja reprezentująca język związany z grupą.
 * Klasa stanowi mapowanie tabeli groups_languages.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link Group} - pole "group"</li>
 *     <li>Relacja wiele do jednego z encją {@link Language} - pole "language"</li>
 * </ul>
 */
@Entity
@Table(name = "groups_languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupLanguage {
    /**
     * Unikalny identyfikator języka grupowego.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Grupa z przypisanym językiem.
     * Relacja wiele do jednego z tabelą groups.
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * Język przypisany do grupy.
     * Relacja wiele do jednego z tabelą languages.
     */
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    /**
     * Konstruktor wiążący grupę z językiem grupowym.
     *
     *
     * @param group grupa z przypisanym językiem
     * @param language język przypisany do grupy
     */
    public GroupLanguage(Group group, Language language) {
        this.group = group;
        this.language = language;
    }
}