package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "multimedia")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false, length = 512)
    @NotNull
    private String url;

    @Column(name = "mimie_type", nullable = false, length = 15)
    @NotNull
    private String mimeType;

    @Column(name = "mimie_subtype", nullable = false, length = 15)
    @NotNull
    private String mimeSubtype;


    public Multimedia(UUID uuid, String url, String mimeType, String mimeSubtype) {
        this.uuid = uuid;
        this.url = url;
        this.mimeType = mimeType;
        this.mimeSubtype = mimeSubtype;
    }
}
