package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Entity
@Table(name = "groups_events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    public GroupEvent(Event event, Group group) {
        this.event = event;
        this.group = group;
    }

    public GroupEvent(Event event) {
        this.event = event;
    }
}
