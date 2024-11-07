package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "groups_events")
public class GroupEvent {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull
    @MapsId
    private Event event;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @NotNull
    private Group group;
}
