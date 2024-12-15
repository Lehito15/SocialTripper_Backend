package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Encja reprezentująca konto w systemie.
 * Klasa stanowi mapowanie na tabelę accounts w bazie danych.
 * <b>Relacje :</b>
 * <ul>
 *     <li>Relacja jeden do jeden z encją {@link User} - pole "user"</li>
 * </ul>
 */

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    /**
     * Unikalny identyfikator konta w tabeli.
     * Automatycznie inkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Globalny, unikalny identyfikator konta w systemie.
     */
    @Column(nullable = false)
    private UUID uuid;

    /**
     * Unikalna nazwa użytkownika w systemie.
     */
    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    /**
     * Email użytkownika powiązany z kontem.
     */
    @Column(nullable = false, unique = true, length = 64)
    private String email;

    /**
     * Poziom dostępności konta dla innych użytkowników systemu.
     * Konto publiczne (wartość true pola) lub prywatne (wartość false pola).
     */
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    /**
     * Numer telefonu użytkownika powiązany z kontem.
     */
    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    /**
     * Rola użytkownika w systemie.
     * Przy zakładaniu konta automatycznie ustawiane na USER.
     */
    @Column(nullable = false, length = 50)
    private String role;

    /**
     * Informacja czy konto zostało zawieszone przez właściciela.
     * Wartość true oznacza konto zawieszone, a wartość false konto aktywne.
     */
    @Column(name = "is_expired")
    private boolean isExpired;

    /**
     * Informacja czy konto zostało zablokowane przez administrację systemu.
     * Wartość true oznacza konto zablokowane, a wartość false konto aktywne.
     */
    @Column(name = "is_locked")
    private boolean isLocked;

    /**
     * Data utworzenia konta w systemie.
     */
    @Column(name = "created_at")
    private LocalDate createdAt;

    /**
     * Odniesienie do zasobu - danych konta w architekturze REST API.
     */
    @Column(name = "home_page_url", nullable = false, unique = true, length = 512)
    private String homePageUrl;

    /**
     * Opis konta użytkownika.
     * Wprowadzany przez właściciela konta w celu przedstawienia się innym użytkownikom.
     */
    @Column(length = 150)
    private String description;

    /**
     * Liczba użytkowników, którzy obserwują właściciela danego konta.
     */
    @Column(name = "followers_number", nullable = false)
    private int followersNumber;

    /**
     * Liczba użtykowników obserwowanych przez właściciela konta.
     */
    @Column(name = "following_number", nullable = false)
    private int followingNumber;

    /**
     * Liczba wydarzeń, których użytkownik jest członkiem.
     */
    @Column(name = "number_of_trips", nullable = false)
    private int numberOfTrips;

    /**
     * Link do zdjęcia profilowego użytkownika przechowywanego w Azure Blob Storage.
     */
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    /**
     * Użytkownik będący właścicielem konta.
     * Relacja jeden do jednego z tabelą users.
     * Relacja jest mapowana przez pole "account" w encji {@link User}.
     */
    @OneToOne(mappedBy = "account")
    private User user;

    /**
     * Tworzy nowy obiekt konta z pełnym zestawem parametrów z pominięciem id, które generowane jest automatycznie.
     *
     * @param uuid globalny, unikalny identyfikator konta
     * @param nickname nazwa użytkownika w aplikacji
     * @param email adres e-mail użytkownika
     * @param isPublic określa, czy konto jest publiczne
     * @param phone numer telefonu użytkownika
     * @param role rola przypisana użytkownikowi ("USER", "ADMIN")
     * @param isExpired określa, czy konto jest zawieszone
     * @param isLocked określa, czy konto jest zablokowane
     * @param createdAt data utworzenia konta
     * @param homePageUrl odniesienie do zasobu konta w REST API
     * @param description opis użytkownika
     * @param followersNumber liczba obserwujących użytkownika
     * @param followingNumber liczba użytkowników, których obserwuje użytkownik
     * @param numberOfTrips liczba wycieczek przypisanych do użytkownika
     * @param profilePictureUrl adres URL zdjęcia profilowego użytkownika
     */
    public Account(UUID uuid, String nickname, String email, boolean isPublic,
                   String phone, String role, boolean isExpired, boolean isLocked,
                   LocalDate createdAt, String homePageUrl, String description, int followersNumber,
                   int followingNumber, int numberOfTrips, String profilePictureUrl) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.email = email;
        this.isPublic = isPublic;
        this.phone = phone;
        this.role = role;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.createdAt = createdAt;
        this.homePageUrl = homePageUrl;
        this.description = description;
        this.followersNumber = followersNumber;
        this.followingNumber = followingNumber;
        this.numberOfTrips = numberOfTrips;
        this.profilePictureUrl = profilePictureUrl;
    }


    /**
     * Tworzy nowy obiekt konta z podstawowym zestawem parametrów.
     *
     * @param uuid globalny, unikalny identyfikator konta
     * @param nickname pseudonim użytkownika
     * @param email adres e-mail użytkownika
     * @param isPublic określa, czy konto jest publiczne
     * @param phone numer telefonu użytkownika
     * @param role rola przypisana użytkownikowi ("USER", "ADMIN")
     * @param homePageUrl odniesienie do zasobu konta w REST API
     * @param description opis użytkownika
     * @param profilePictureUrl adres URL zdjęcia profilowego użytkownika
     */
    public Account(UUID uuid, String nickname, String email, boolean isPublic,
                   String phone, String role, String homePageUrl, String description,
                   String profilePictureUrl) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.email = email;
        this.isPublic = isPublic;
        this.phone = phone;
        this.role = role;
        this.homePageUrl = homePageUrl;
        this.description = description;
        this.profilePictureUrl = profilePictureUrl;
    }
}
