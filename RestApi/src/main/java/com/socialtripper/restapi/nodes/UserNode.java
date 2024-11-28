package com.socialtripper.restapi.nodes;

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

//    @Relationship(type = "POSTS")
//    private Set<PostNode> posts;

    @Relationship(type = "IS_GROUP_MEMBER")
    private Set<GroupNode> groups;

    @Relationship(type = "APPLIES_FOR_GROUP")
    private Set<GroupNode> appliedGroups;

    @Relationship(type = "REQUESTS_FOLLOW")
    private Set<UserNode> requestedFollows;

    @Relationship(type = "FOLLOWS")
    private Set<UserNode> follows;

    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.INCOMING)
    private Set<UserNode> followedBy;

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
        //this.posts = new HashSet<>();
        this.groups = new HashSet<>();
        this.appliedGroups = new HashSet<>();
        this.requestedFollows = new HashSet<>();
        this.follows = new HashSet<>();
        this.followedBy = new HashSet<>();
    }

}
