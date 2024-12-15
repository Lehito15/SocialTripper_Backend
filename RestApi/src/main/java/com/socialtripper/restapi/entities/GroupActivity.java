package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Encja reprezentująca aktywności grup.
 * Klasa stanowi mapowanie tabeli groups_activities.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link Group} - pole "group"</li>
 *     <li>Relacja wiele do jednego z encją {@link Activity} - pole "activity"</li>
 * </ul>
 */
@Entity
@Table(name = "groups_activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupActivity {
    /**
     * Unikalny identyfikator aktywności grupowej w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Grupa posiadająca przypisaną aktywność.
     * Relacja wiele do jednego z tabelą groups.
     */
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * Aktywność przypisana do grupy.
     * Relacja wiele do jednego z tabelą activities.
     */
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    /**
     * Konstruktor wiążący grupę z typem aktywności.
     *
     * @param group grupa z przypisaną aktywnością
     * @param activity aktywność przypisana do grupy
     */
    public GroupActivity(Group group, Activity activity) {
        this.group = group;
        this.activity = activity;
    }
}
