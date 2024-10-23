package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "multimedia")
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "multimedia_id")
    private UUID id;

    @Column(nullable = false, length = 512)
    @NotNull
    private String url;

    @Column(name = "mimie_type", nullable = false, length = 15)
    @NotNull
    private String mimeType;

    @Column(name = "mimie_subtype", nullable = false, length = 15)
    @NotNull
    private String mimeSubtype;

    @ManyToOne
    @JoinColumn(name = "multimedia_category_id")
    private MultimediaCategory multimediaCategory;
}
