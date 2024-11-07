package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "messages_multimedia")
public class MessageMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    @NotNull
    private Message message;

    @ManyToOne
    @JoinColumn(name = "multimedia_id", nullable = false)
    @NotNull
    private Multimedia multimedia;
}
