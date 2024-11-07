package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {
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

    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
