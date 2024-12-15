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
 * Encja reprezentująca użytkownika systemu - osobę fizyczną.
 * Klasa stanowi mapowanie tabeli users.
 * <b>Relaje: </b>
 * <ul>
 *     <li>Relacja jeden do jednego z encją {@link Account} - pole "account"</li>
 *     <li>Relacja wiele do jednego z encją {@link Country} - pole "country"</li>
 *     <li>Relacja jeden do wielu z encją {@link UserLanguage - pole "userLanguages"}</li>
 *     <li>Relacja jeden do wielu z encją {@link UserActivity} - pole "userActivities"</li>
 * </ul>
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    /**
     * Unikalny identyfikator użytkownika.
     * Autoinkrementowany klucz główny.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Globalny, unikalny identyfikator.
     */
    @Column(nullable = false)
    private UUID uuid;

    /**
     * Imię użytkownika.
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * Nazwisko użtykownika.
     */
    @Column(nullable = false, length = 50)
    private String surname;

    /**
     * Płeć użytkownika.
     * Symbol 'f' dla kobiet, 'm' dla mężczyzn.
     */
    @Column(nullable = false)
    private Character gender;

    /**
     * Data urodzenia.
     */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Waga w kilogramach.
     */
    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal weight;

    /**
     * Wzrost w metrach.
     */
    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal height;

    /**
     * Bmi, obliczne automatycznie na podstawie wzrostu i wagi.
     * Bmi = waga / (wzrost * wzrost).
     */
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal bmi;

    /**
     * Ocena fizyczności własnej fizyczności dokonywana przez użytkownika przy zakładaniu konta.
     */
    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal physicality;

    /**
     * Konto użtykownika.
     */
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Kraj pochodzenia użytkownika.
     */
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    /**
     * Języki, w których porozumiewa się użytkownika.
     */
    @OneToMany(mappedBy = "user")
    private Set<UserLanguage> userLanguages;

    /**
     * Aktywności użytkownika
     */
    @OneToMany(mappedBy = "user")
    private Set<UserActivity> userActivities;

    /**
     * Konstruktor z parametrami fizycznymi użytkownika.
     * Generowany jest identyfikator UUID.
     *
     * @param uuid globalny, unikalny identyfikator
     * @param name imię
     * @param surname nazwisko
     * @param gender płeć
     * @param dateOfBirth data urodzenia
     * @param weight waga
     * @param height wzrost
     * @param bmi bmi
     * @param physicality ocena fizyczności
     */
    public User(UUID uuid, String name, String surname, Character gender,
                LocalDate dateOfBirth, BigDecimal weight, BigDecimal height,
                BigDecimal bmi, BigDecimal physicality) {
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.physicality = physicality;
        this.userActivities = new HashSet<>();
        this.userLanguages = new HashSet<>();
    }

}
