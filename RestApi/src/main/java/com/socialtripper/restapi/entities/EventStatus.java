package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "events_statuses")
public class EventStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_status_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    @NotNull
    private String name;
}
