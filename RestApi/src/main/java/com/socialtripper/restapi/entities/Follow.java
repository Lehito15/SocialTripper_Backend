package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "follow_id")
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    @NotNull
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    @NotNull
    private User followed;
}
