package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "reports_statuses")
public class ReportStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;
}
