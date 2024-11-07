package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Node
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    private String name;
    private boolean isPublic;
    private String status;
    private int participantsNumber;
    private int maxParticipantsNumber;
    private String iconUrl;
    private Double startLocationLatitude;
    private Double startLocationLongitude;
    private Double destinationLocationLatitude;
    private Double destinationLocationLongitude;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String destination;
    private String eventUrl;
    private Set<String> languages;
    private Set<String> activities;

    @Relationship(type = "EVENT_OWNED_BY_GROUP")
    private Group group;

    @Relationship(type = "EVENT_NOTIFIES")
    private Notification generatedNotification;
}
