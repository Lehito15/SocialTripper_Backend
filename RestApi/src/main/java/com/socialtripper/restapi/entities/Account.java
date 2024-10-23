package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    @NotNull
    private String nickname;

    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    private String email;

    @Column(name = "is_public", nullable = false)
    @NotNull
    private boolean isPublic;

    @Column(nullable = false, length = 16)
    @NotNull
    private String salt;

    @Column(nullable = false, unique = true, length = 20)
    @NotNull
    private String phone;

    @Column(nullable = false, length = 50)
    @NotNull
    private String role;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDate createdAt;

    @Column(name = "home_page_url", nullable = false, unique = true, length = 512)
    @NotNull
    private String homePageUrl;

    @Column(length = 150)
    private String description;

    @Column(name = "followers_number", nullable = false)
    @NotNull
    private int followersNumber;

    @Column(name = "following_number", nullable = false)
    @NotNull
    private int followingNumber;

    @Column(name = "number_of_trips", nullable = false)
    @NotNull
    private int numberOfTrips;

    @ManyToOne
    @JoinColumn(name = "profile_picture_id")
    private Multimedia profilePicture;

}
