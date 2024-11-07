package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "banned_posts")
public class BannedPost {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;
}
