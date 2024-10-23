package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "language_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 35)
    @NotNull
    private String name;
}
