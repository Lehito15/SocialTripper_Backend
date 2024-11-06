package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.UUID;

@Node
public class EventRelation {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    private String mediaUrl;
    private String thumbnailUrl;
    private int commentsNumber;
    private int reactionsNumber;
    private boolean isPublic;

    @Relationship(type = "RELATION_NOTIFIES")
    private Notification generatedNotification;
}
