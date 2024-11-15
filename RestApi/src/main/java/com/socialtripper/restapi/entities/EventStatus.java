package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "events_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventStatus {
    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @NotNull
    private String name;
}
