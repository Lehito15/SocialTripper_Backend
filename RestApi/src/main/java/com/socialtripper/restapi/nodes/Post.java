package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Node
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    private String content;
    private boolean isExpired;
    private boolean isLocked;
    private LocalDateTime postTime;
    private int commentsNumber;
    private int reactionsNumber;
    private Set<String> tags;
    private Set<String> media;

    @Relationship(type = "POST_NOTIFIES")
    private Notification generatedNotification;

    @Relationship(type = "POSTED_IN_GROUP")
    private Group group;

    @Relationship(type = "POSTED_IN_EVENT")
    private Event event;
}
