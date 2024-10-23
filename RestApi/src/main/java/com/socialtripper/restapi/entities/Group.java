package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_id")
    private UUID id;

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
    private Account account;

    @ManyToOne
    @JoinColumn(name = "profile_picture_id")
    private Multimedia icon;


}
