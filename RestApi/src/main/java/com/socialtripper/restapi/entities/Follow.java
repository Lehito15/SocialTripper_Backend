package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "follows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    @NotNull
    private Account follower;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    @NotNull
    private Account followed;

    @Column(name = "following_since")
    private LocalDate followingSince;

    @Column(name = "following_to")
    private LocalDate followingTo;

    public Follow(Account follower, Account followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public Follow(Account follower, Account followed, LocalDate followingSince) {
        this.follower = follower;
        this.followed = followed;
        this.followingSince = followingSince;
    }
}
