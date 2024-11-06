package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
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

    @Column(nullable = false, precision = 2, scale = 1)
    @NotNull
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

}
