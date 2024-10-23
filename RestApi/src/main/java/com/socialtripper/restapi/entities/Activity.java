package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "activity_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    @NotNull
    private String name;
}
