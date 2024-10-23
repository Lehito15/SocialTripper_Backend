package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "messages_multimedia")
public class MessageMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_multimedia_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    @NotNull
    private Message message;

    @ManyToOne
    @JoinColumn(name = "multimedia_id", nullable = false)
    @NotNull
    private Multimedia multimedia;
}
