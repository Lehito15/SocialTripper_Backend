package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "locations_scopes")
public class LocationScope {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;
}
