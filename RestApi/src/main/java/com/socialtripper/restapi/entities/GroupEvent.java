package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "groups_events")
public class GroupEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_event_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull
    private Event event;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @NotNull
    private Group group;
}
