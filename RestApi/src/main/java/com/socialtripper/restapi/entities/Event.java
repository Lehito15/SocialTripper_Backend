package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Encja reprezentująca wydarzenie.
 * Klasa stanowi mapowanie na tabele events w bazie danych.
 * <b>Relacje: </b>
 * <ul>
 *     <li>Relacja wiele do jedenego z encją {@link EventStatus} - pole "eventStatus"</li>
 *     <li>Relacja wiele do jedenego z encją {@link Account} - pole "owner"</li>
 *     <li>Relcja jeden do wielu z encją {@link EventActivity} - pole "eventActivities"</li>
 *     <li>Relacja jeden do wielu z encją {@link EventLanguage} - pole "eventLanguages"</li>
 * </ul>
 */
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
    /**
     * Unikalny identyfikator wydarzenia w tabeli.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Globalny, unikalny identyfikator wydarzenia w systemie.
     */
    @Column(nullable = false)
    private UUID uuid;

    /**
     * Nazwa wydarzenia.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Słowny opis celu wydarzenia.
     */
    @Column(length = 100)
    private String destination;

    /**
     * Opis dodawny przez założyciela, który pozwala.
     * Użytkownikom bliżej zapoznać się z wydarzeniem.
     */
    @Column(length = 5000)
    private String description;

    /**
     * Zasady obowiązujące podczas trwania wydarzenia.
     * Dodane przez właściciela wydarzenia.
     */
    @Column(length = 5000)
    private String rules;

    /**
     * Informacja o poziomie dostępu do grupy.
     * Publiczna dla wartości true pola, prywatna dla wartości false.
     */
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    /**
     * Data założenia wydarzenia.
     */
    @Column(name = "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;

    /**
     * Czas rozpoczęcia wydarzenia.
     */
    @Column(name = "event_start_time")
    private LocalDateTime eventStartTime;

    /**
     * Czas zakończenia wydarzenia.
     */
    @Column(name = "event_end_time")
    private LocalDateTime eventEndTime;

    /**
     * Liczba uczestników wydarzenia.
     */
    @Column(name = "number_of_participants", nullable = false)
    private int numberOfParticipants;

    /**
     * Maksymalna liczba uczestników dla wydarzenia.
     * W przypadku braku limitu miejsc usawiana na wartość -1.
     */
    @Column(name = "max_number_of_participants")
    private int maxNumberOfParticipants;

    /**
     * Długość geograficzna miejsca rozpoczęcia wydarzenia.
     */
    @Column(name = "start_longitude", precision = 15, scale =8)
    private BigDecimal startLongitude;

    /**
     * Szerokość geograficzna miejsca rozpoczęcia wydarzenia.
     */
    @Column(name = "start_latitude", precision = 15, scale =8)
    private BigDecimal startLatitude;

    /**
     * Długość geograficzna miejsca zakończenia wydarzenia.
     */
    @Column(name = "stop_longitude", precision = 15, scale =8)
    private BigDecimal stopLongitude;

    /**
     * Szerokość geograficzna miejsca zakończenia wydarzenia.
     */
    @Column(name = "stop_latitude", precision = 15, scale =8)
    private BigDecimal stopLatitude;

    /**
     * Odniesienie do zasobu wydarzenia w REST API.
     */
    @Column(name = "home_page_url", nullable = false, unique = true, length = 512)
    private String homePageUrl;

    /**
     * Link do ikony wydarzenia w Azure Blob Storage
     */
    @Column(name = "icon_url")
    private String iconUrl;

    /**
     * Aktualny status wydarzenia.
     * Relacja wiele do jednego z tabelą events_statuses.
     * Możliwe wartości:
     * <ul>
     *     <li>created</li>
     *     <li>in progress</li>
     *     <li>finished</li>
     *     <li>cancelled</li>
     * </ul>
     * W chwili utworzenia ustawiany na created.
     */
    @ManyToOne
    @JoinColumn(name = "event_status_id", nullable = false)
    private EventStatus eventStatus;

    /**
     * Właściciel wydarzenia.
     * Relacja wiele do jednego z tabelą accounts.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Account owner;

    /**
     * Lista aktywności przypisanych do wydarzenia.
     * Relacja jest mapowana przez pole "event" w encji {@link EventActivity}.
     */
    @OneToMany(mappedBy = "event")
    private Set<EventActivity> eventActivities;

    /**
     * List języków przypisanych do wydarzenia.
     * Relacja jest mapowana przez pole "event" w encji {@link EventLanguage}.
     */
    @OneToMany(mappedBy = "event")
    private Set<EventLanguage> eventLanguages;


    /**
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @param name nazwa wydarzenia
     * @param destination słowny opis celu wydarzenia
     * @param description opis wydarzenia
     * @param rules zasady obowiązujące podczas wydarzenia
     * @param isPublic określa czy wydarzenie jest publiczne czy prywatne
     * @param dateOfCreation data utworzenia wydarzenia
     * @param eventStartTime czas rozpoczącia wydarzenia
     * @param eventEndTime czas zakończenia wydarzenia
     * @param numberOfParticipants liczba członków wydarzenia
     * @param maxNumberOfParticipants maksymalna liczba członków wydarzenia
     * @param startLongitude długość geograficzna miejsca rozpoczęcia wydarzenia
     * @param startLatitude szerokość geograficzna miejsca rozpoczęcia wydarzenia
     * @param stopLongitude długość geograficzna miejsca zakończenia wydarzenia
     * @param stopLatitude szerokość geograiczna miejsca zakończenia wydarzenia
     * @param homePageUrl odniesienie do zasobu wydarzenia w systemie
     * @param eventStatus status wydarzenia
     * @param owner właściciel wydarzenia
     * @param iconUrl link do ikony wydarzenia
     * @param eventActivities lista aktywności przypisanych wydarzeniu
     * @param eventLanguages lista języków przypisanych wydarzeniu
     */
    public Event(UUID uuid, String name, String destination, String description, String rules, Boolean isPublic, LocalDate dateOfCreation,
                 LocalDateTime eventStartTime, LocalDateTime eventEndTime, int numberOfParticipants,
                 int maxNumberOfParticipants, BigDecimal startLongitude, BigDecimal startLatitude, BigDecimal stopLongitude,
                 BigDecimal stopLatitude, String homePageUrl,
                 EventStatus eventStatus, Account owner, String iconUrl, Set<EventActivity> eventActivities,
                 Set<EventLanguage> eventLanguages) {
        this.uuid = uuid;
        this.name = name;
        this.destination = destination;
        this.description = description;
        this.rules = rules;
        this.isPublic = isPublic;
        this.dateOfCreation = dateOfCreation;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.numberOfParticipants = numberOfParticipants;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.startLongitude = startLongitude;
        this.startLatitude = startLatitude;
        this.stopLongitude = stopLongitude;
        this.stopLatitude = stopLatitude;
        this.homePageUrl = homePageUrl;
        this.eventStatus = eventStatus;
        this.owner = owner;
        this.iconUrl = iconUrl;
        this.eventActivities = eventActivities;
        this.eventLanguages = eventLanguages;
    }


}
