package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Encja reprezentująca grupę.
 * Klasa stanowi mapowanie na tabelę groups.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jednego z encją {@link LocationScope} - pole "locationScope"</li>
 *     <li>Relacja wiele do jedneog z encją {@link Account} - pole "owner"</li>
 *     <li>Relacja jeden do wielu z encją {@link GroupActivity} - pole "groupActivities"</li>
 *     <li>Relacja jeden do wielu z encją {@link GroupLanguage} - pole "groupLanguages"</li>
 * </ul>
 */
@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Group {
    /**
     * Unikalny identyfikator grupy w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Globalny, unikalny identyfikator grupy w systemie.
     */
    @Column(nullable = false)
    private UUID uuid;

    /**
     * Nazwa grupy.
     */
    @Column(nullable = false, length = 128)
    private String name;

    /**
     * Aktualna liczba członków grupy.
     */
    @Column(name = "number_of_members", nullable = false)
    private int numberOfMembers;

    /**
     * Informacja o dostępie do grupy.
     * Publiczna gdy wartość pola ustawiona na true, prywatna gdy wartość ustawiona na false.
     */
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    /**
     * Opis grupy ustawiany przez właściciela grupy.
     * Umożliwa bliższe zapoznanie się z grupą.
     */
    @Column(length = 5000)
    private String description;

    /**
     * Zasady obowiązujące uczestników grupy.
     */
    @Column(length = 5000)
    private String rules;

    /**
     * Data utworzenia grupy.
     */
    @Column(name = "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;

    /**
     * Odniesienie do zasobu grupy w REST API.
     */
    @Column(name = "home_page_url", nullable = false, unique = true, length = 512)
    private String homePageUrl;

    /**
     * Długość geograficzna lokalizacji grupy.
     */
    @Column(name = "location_longitude", precision = 15, scale = 8)
    private BigDecimal locationLongitude;

    /**
     * Szerokość geograficzna lokalizacji grupy.
     */
    @Column(name = "location_latitude", precision = 15, scale = 8)
    private BigDecimal locationLatitude;

    /**
     * Link do ikony grupy w Azure Blob Storage.
     */
    @Column(name = "icon_url")
    private String iconUrl;

    /**
     * Zasięg grupy - kraj / miasto.
     * Relacja wiele do jednego z tabelą location_scopes.
     */
    @ManyToOne
    @JoinColumn(name = "location_scope_id")
    private LocationScope locationScope;

    /**
     * Właściciel grupy.
     * Relacja wiele do jednego z tabelą accounts.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Account owner;

    /**
     * Zbiór aktywności przypisanych do grupy.
     * Relacja jeden do wielu z tabelą groups_activities.
     * Mapowane przez pole "group" w encji {@link GroupActivity}.
     */
    @OneToMany(mappedBy = "group")
    private Set<GroupActivity> groupActivities;

    /**
     * Zbiór języków przypisanych do grupy.
     * Relacja jeden do wielu z tablą groups_languages.
     * Mapowane przez pole "group" w encji {@link GroupLanguage}.
     */
    @OneToMany(mappedBy = "group")
    private Set<GroupLanguage> groupLanguages;

    /**
     * Konstruktor grupy pomijający autoinkrementowany klucz główny oraz pola wynikające z kluczy obcych w encji.
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @param name nazwa grupy
     * @param numberOfMembers liczba członków grupy
     * @param isPublic informacja o dostępie do grupy
     * @param description opis grupy
     * @param rules zasady panujące w grupie
     * @param dateOfCreation data założenia grupy
     * @param homePageUrl odniesienie do zasobu grupy
     * @param locationLongitude długość geograficzna lokalizacji grupy
     * @param locationLatitude szerokość geograficzna lokalizacji grupy
     */
    public Group(UUID uuid, String name, int numberOfMembers,
                 Boolean isPublic, String description, String rules,
                 LocalDate dateOfCreation, String homePageUrl, BigDecimal locationLongitude,
                 BigDecimal locationLatitude) {
        this.uuid = uuid;
        this.name = name;
        this.numberOfMembers = numberOfMembers;
        this.isPublic = isPublic;
        this.description = description;
        this.rules = rules;
        this.dateOfCreation = dateOfCreation;
        this.homePageUrl = homePageUrl;
        this.locationLongitude = locationLongitude;
        this.locationLatitude = locationLatitude;
        this.groupActivities = new HashSet<>();
        this.groupLanguages = new HashSet<>();
    }

}
