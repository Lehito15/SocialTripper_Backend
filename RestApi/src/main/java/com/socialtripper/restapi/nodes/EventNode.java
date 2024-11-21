package com.socialtripper.restapi.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Node("EVENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventNode {
    @Id
    private String id;

    private UUID uuid;
    private String name;
    private boolean isPublic;
    private String iconUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String destination;
    private String homePageUrl;
    private Set<String> languages;
    private Set<String> activities;

    @Relationship(type = "IS_OWNED_BY_USER")
    private UserNode owner;



    public EventNode(UUID uuid, String name, boolean isPublic,
                     String iconUrl, LocalDateTime startTime, LocalDateTime endTime,
                     String destination, String eventUrl, Set<String> languages,
                     Set<String> activities) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.name = name;
        this.isPublic = isPublic;
        this.iconUrl = iconUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.destination = destination;
        this.homePageUrl = eventUrl;
        this.languages = languages;
        this.activities = activities;
    }
}
