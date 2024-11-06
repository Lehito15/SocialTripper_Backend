package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    public Account(UUID uuid, String nickname, String email, boolean isPublic, String salt, String phone, String role, boolean isExpired, boolean isLocked, LocalDate createdAt, String homePageUrl, String description, int followersNumber, int followingNumber, int numberOfTrips, Multimedia profilePicture) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.email = email;
        this.isPublic = isPublic;
        this.salt = salt;
        this.phone = phone;
        this.role = role;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.createdAt = createdAt;
        this.homePageUrl = homePageUrl;
        this.description = description;
        this.followersNumber = followersNumber;
        this.followingNumber = followingNumber;
        this.numberOfTrips = numberOfTrips;
        this.profilePicture = profilePicture;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

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
