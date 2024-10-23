package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "events_activities")
public class EventActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_activity_id")
    private UUID id;

    @Column(name = "required_experience", nullable = false, precision = 2, scale = 1)
    private BigDecimal requiredExperience;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull
    private Event event;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    @NotNull
    private Activity activity;
}