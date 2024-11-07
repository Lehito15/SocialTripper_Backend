package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "posts_reports")
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

}
