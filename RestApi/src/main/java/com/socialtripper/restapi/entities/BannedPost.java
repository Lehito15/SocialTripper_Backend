package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "banned_posts")
public class BannedPost {
    @Id
    @Column(name = "banned_post_id")
    private UUID id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;
}
