package com.socialtripper.restapi.entities;


import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_id")
    private UUID id;

    @Column(length = 2220)
    private String content;

    @Column(name = "date_of_post", nullable = false)
    private LocalDate dateOfPost;

    @Column(name = "is_expired", nullable = false)
    @NotNull
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    @NotNull
    private boolean isLocked;

    @Column(name = "commments_number", nullable = false)
    @NotNull
    private int commentsNumber;

    @Column(name = "reactions_number", nullable = false)
    @NotNull
    private int reactionsNumber;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @NotNull
    private Account account;

    @OneToMany(mappedBy = "post")
    private Set<PostMultimedia> postMultimedia;

}
