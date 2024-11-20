package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
    public Event(UUID uuid, String destination, String description, String rules, Boolean isPublic, LocalDate dateOfCreation,
                 LocalDateTime eventStartTime, LocalDateTime eventEndTime, int numberOfParticipants, int actualNumberOfParticipants,
                 int maxNumberOfParticipants, BigDecimal startLongitude, BigDecimal startLatitude, BigDecimal stopLongitude,
                 BigDecimal stopLatitude, BigDecimal destinationLongitude, BigDecimal destinationLatitude, String homePageUrl,
                 EventStatus eventStatus, Relation relation, Account owner, String iconUrl, Set<EventActivity> eventActivities,
                 Set<EventLanguage> eventLanguages) {
        this.uuid = uuid;
        this.destination = destination;
        this.description = description;
        this.rules = rules;
        this.isPublic = isPublic;
        this.dateOfCreation = dateOfCreation;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.numberOfParticipants = numberOfParticipants;
        this.actualNumberOfParticipants = actualNumberOfParticipants;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.startLongitude = startLongitude;
        this.startLatitude = startLatitude;
        this.stopLongitude = stopLongitude;
        this.stopLatitude = stopLatitude;
        this.destinationLongitude = destinationLongitude;
        this.destinationLatitude = destinationLatitude;
        this.homePageUrl = homePageUrl;
        this.eventStatus = eventStatus;
        this.relation = relation;
        this.owner = owner;
        this.iconUrl = iconUrl;
        this.eventActivities = eventActivities;
        this.eventLanguages = eventLanguages;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(length = 100)
    private String destination;

    @Column(length = 5000)
    private String description;

    @Column(length = 5000)
    private String rules;

    @Column(name = "is_public", nullable = false)
    @NotNull
    private Boolean isPublic;

    @Column(name = "date_of_creation", nullable = false)
    private LocalDate dateOfCreation;

    @Column(name = "event_start_time")
    private LocalDateTime eventStartTime;

    @Column(name = "event_end_time")
    private LocalDateTime eventEndTime;

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

    @OneToOne
    @JoinColumn(name = "relation_id")
    private Relation relation;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @NotNull
    private Account owner;

    @Column(name = "icon_url")
    private String iconUrl;

    @OneToMany(mappedBy = "event")
    private Set<EventActivity> eventActivities;

    @OneToMany(mappedBy = "event")
    private Set<EventLanguage> eventLanguages;

}
