package com.socialtripper.restapi.nodes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
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
    private Boolean isPublic;
    private Boolean isLocked;
    private String profileUrl;
    private Set<String> languages;
    private Set<String> activities;

    @Relationship(type = "APPLIES_FOR_EVENT")
    @JsonIgnore
    private Set<EventNode> appliedEvents;

    @Relationship(type = "OWNS_EVENT")
    @JsonIgnore
    private Set<EventNode> ownedEvents;

    @Relationship(type = "IS_EVENT_MEMBER")
    @JsonIgnore
    private Set<EventMembership> events;




    @Relationship(type = "FOLLOWS")
    @JsonIgnore
    private Set<Follows> follows;

    @Relationship(type = "REACTS_TO_COMMENT")
    @JsonIgnore
    private Set<Comment> reactedComments;

    @Relationship(type = "COMMENTS")
    @JsonIgnore
    private Set<Comment> comments;

    @Relationship(type = "REQUEST_FOLLOW")
    @JsonIgnore
    private Set<UserNode> requestedFollows;

    @Relationship(type = "POSTS")
    @JsonIgnore
    private Set<PostNode> posts;

    @Relationship(type = "BELONGS_TO_GROUP")
    @JsonIgnore
    private Set<GroupNode> groups;

    @Relationship(type = "APPLIES_FOR_GROUP")
    @JsonIgnore
    private Set<GroupNode> appliedGroups;

    public UserNode(UUID uuid, String name, String surname,
                    String nickname, boolean isPublic, boolean isLocked,
                    String profileUrl, Set<String> languages, Set<String> activities) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.isPublic = isPublic;
        this.isLocked = isLocked;
        this.profileUrl = profileUrl;
        this.languages = languages;
        this.activities = activities;
    }

}
