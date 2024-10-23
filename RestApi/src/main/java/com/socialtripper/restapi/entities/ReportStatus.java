package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "reports_statuses")
public class ReportStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_status_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;
}
