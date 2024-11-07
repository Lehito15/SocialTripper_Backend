package com.socialtripper.restapi.entities;


import jakarta.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    @NotNull
    private String name;
}
