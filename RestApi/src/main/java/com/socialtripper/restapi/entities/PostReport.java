package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "posts_reports")
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_report_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

}
