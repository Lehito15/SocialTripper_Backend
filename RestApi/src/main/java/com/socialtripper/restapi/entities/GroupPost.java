package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "groups_posts")
public class GroupPost {
    @Id
    @Column(name = "group_post_id")
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
