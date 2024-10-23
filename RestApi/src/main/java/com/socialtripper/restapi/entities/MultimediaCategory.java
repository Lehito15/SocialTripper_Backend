package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "multimedia_categories")
public class MultimediaCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "multimedia_category_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    @NotNull
    private String name;
}
