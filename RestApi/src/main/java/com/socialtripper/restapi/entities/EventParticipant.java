package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "events_participants")
public class EventParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_participant_id")
    private UUID id;

    @Column(name = "is_actual_participant")
    private boolean isActualParticipant;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull
    private Event event;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull
    private Account participant;
}