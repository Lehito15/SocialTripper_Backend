package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id")
    private UUID id;

    @Column(length = 5000)
    private String description;

    @Column(length = 5000)
    private String rules;

    @Column(name = "is_public", nullable = false)
    @NotNull
    private Boolean isPublic;

    @Column(name = "date_of_creation", nullable = false)
    @NotNull
    private LocalDate dateOfCreation;

    @Column(name = "number_of_participants", nullable = false)
    @NotNull
    private int numberOfParticipants;

    @Column(name = "actual_number_of_participants")
    private int actualNumberOfParticipants;

    @Column(name = "max_number_of_participants")
    private int maxNumberOfParticipants;

    @Column(name = "start_longitude", precision = 15, scale =8)
    private BigDecimal startLongitude;

    @Column(name = "start_latitude", precision = 15, scale =8)
    private BigDecimal startLatitude;

    @Column(name = "stop_longitude", precision = 15, scale =8)
    private BigDecimal stopLongitude;

    @Column(name = "stop_latitude", precision = 15, scale =8)
    private BigDecimal stopLatitude;

    @Column(name = "destination_longitude", precision = 15, scale =8)
    private BigDecimal destinationLongitude;

    @Column(name = "destination_latitude", precision = 15, scale =8)
    private BigDecimal destinationLatitude;

    @Column(name = "home_page_url", nullable = false, unique = true, length = 512)
    @NotNull
    private String homePageUrl;

    @ManyToOne
    @JoinColumn(name = "event_status_id", nullable = false)
    @NotNull
    private EventStatus eventStatus;

    @ManyToOne
    @JoinColumn(name = "relation_id")
    private Relation relation;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @NotNull
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "icon_id")
    private Multimedia icon;

}
