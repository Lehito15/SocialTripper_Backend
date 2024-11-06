package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Group {
    public Group(UUID uuid, String name, int numberOfMembers, Boolean isPublic, String description, String rules, LocalDate dateOfCreation, String homePageUrl, BigDecimal locationLongitude, BigDecimal locationLatitude, LocationScope locationScope, Account owner, Multimedia icon) {
        this.uuid = uuid;
        this.name = name;
        this.numberOfMembers = numberOfMembers;
        this.isPublic = isPublic;
        this.description = description;
        this.rules = rules;
        this.dateOfCreation = dateOfCreation;
        this.homePageUrl = homePageUrl;
        this.locationLongitude = locationLongitude;
        this.locationLatitude = locationLatitude;
        this.locationScope = locationScope;
        this.owner = owner;
        this.icon = icon;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false, length = 128)
    @NotNull
    private String name;

    @Column(name = "number_of_members", nullable = false)
    @NotNull
    private int numberOfMembers;

    @Column(name = "is_public", nullable = false)
    @NotNull
    private Boolean isPublic;

    @Column(length = 5000)
    private String description;

    @Column(length = 5000)
    private String rules;

    @Column(name = "date_of_creation", nullable = false)
    @NotNull
    private LocalDate dateOfCreation;

    @Column(name = "home_page_url", nullable = false, unique = true, length = 512)
    @NotNull
    private String homePageUrl;

    @Column(name = "location_longitude", precision = 15, scale = 8)
    private BigDecimal locationLongitude;

    @Column(name = "location_latitude", precision = 15, scale = 8)
    private BigDecimal locationLatitude;

    @ManyToOne
    @JoinColumn(name = "location_scope_id")
    private LocationScope locationScope;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @NotNull
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "profile_picture_id")
    private Multimedia icon;

}
