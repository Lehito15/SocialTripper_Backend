package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "personal_posts")
public class PersonalPost {
    @Id
    @Column(name = "personal_post_id")
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
