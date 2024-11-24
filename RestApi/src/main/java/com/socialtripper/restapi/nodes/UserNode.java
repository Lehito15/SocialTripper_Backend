package com.socialtripper.restapi.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node("USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNode {
    @Id
    private String id;

    private UUID uuid;
    private String name;
    private String surname;
    private String nickname;
    private String profileUrl;
    private Set<String> languages;
    private Set<String> activities;

    @Relationship(type = "APPLIES_FOR_EVENT")
    private Set<EventNode> appliedEvents;

    @Relationship(type = "OWNS_EVENT")
    private Set<EventNode> ownedEvents;

    @Relationship(type = "IS_EVENT_MEMBER")
    private Set<EventMembership> events;

    @Relationship(type = "POSTS")
    private Set<PostNode> posts;

    @Relationship(type = "REQUEST_FOLLOW")
    private Set<UserNode> requestedFollows;

    @Relationship(type = "FOLLOWS")
    private Set<UserNode> follows;






    @Relationship(type = "BELONGS_TO_GROUP")
    @JsonIgnore
    private Set<GroupNode> groups;

    @Relationship(type = "APPLIES_FOR_GROUP")
    @JsonIgnore
    private Set<GroupNode> appliedGroups;

    public UserNode(UUID uuid, String name, String surname,
                    String nickname, String profileUrl,
                    Set<String> languages, Set<String> activities) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.languages = languages;
        this.activities = activities;
        this.appliedEvents = new HashSet<>();
        this.ownedEvents = new HashSet<>();
        this.events = new HashSet<>();
        this.posts = new HashSet<>();
    }

}
