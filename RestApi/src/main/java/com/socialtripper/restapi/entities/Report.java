package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_id")
    private UUID id;

    @Column(length = 1000)
    private String decision;

    @ManyToOne
    @JoinColumn(name = "report_status_id", nullable = false)
    @NotNull
    private ReportStatus reportStatus;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    @NotNull
    private Account reporter;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Employee employee;


}
