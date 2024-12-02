package com.socialtripper.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false, length = 50)
    @NotNull
    private String name;

    @Column(nullable = false, length = 50)
    @NotNull
    private String surname;

    @Column(nullable = false)
    @NotNull
    private Character gender;

    @Column(name = "date_od_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    @Column(nullable = false, precision = 4, scale = 1)
    @NotNull
    private BigDecimal weight;

    @Column(nullable = false, precision = 3, scale = 2)
    @NotNull
    private BigDecimal height;

    @Column(nullable = false, precision = 4, scale = 2)
    @NotNull
    private BigDecimal bmi;

    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal physicality;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "user")
    private Set<UserLanguage> userLanguages;

    @OneToMany(mappedBy = "user")
    private Set<UserActivity> userActivities;

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
