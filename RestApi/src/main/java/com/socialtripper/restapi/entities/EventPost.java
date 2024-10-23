package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "events_posts")
public class EventPost {
    @Id
    @Column(name = "event_post_id")
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}