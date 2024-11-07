package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;
import java.util.UUID;

@Node
public class Group {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    private String name;
    private boolean isPublic;
    private String iconUrl;
    private String scope;
    private String profileUrl;
    private Double locationLatitude;
    private Double locationLongitude;
    private Set<String> languages;
    private Set<String> activities;

    @Relationship(type = "GROUP_NOTIFIES")
    private Notification generatedNotification;
}
