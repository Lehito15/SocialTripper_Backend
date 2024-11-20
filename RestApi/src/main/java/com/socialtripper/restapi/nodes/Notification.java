package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.Set;

@Node
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    private String type;
    private String sourceUrl;
    private String iconUrl;
    private String content;
    private LocalDateTime generationTime;
    private LocalDateTime expirationTime;

    @Relationship(type = "NOTIFIES")
    private Set<UserNode> notificationUsers;
}
