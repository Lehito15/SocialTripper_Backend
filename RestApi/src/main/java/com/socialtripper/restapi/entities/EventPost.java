package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events_posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventPost {
    @Id
    private Long id;

    public EventPost(Post post, Event event) {
        this.post = post;
        this.event = event;
    }

    public EventPost(Post post) {
        this.post = post;
    }

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}