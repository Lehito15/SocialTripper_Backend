package com.socialtripper.restapi.entities;


import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "posts_multimedia")
public class PostMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_multimedia_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @NotNull
    private Post post;

    @ManyToOne
    @JoinColumn(name = "multimedia_id", nullable = false)
    @NotNull
    private Multimedia multimedia;
}
