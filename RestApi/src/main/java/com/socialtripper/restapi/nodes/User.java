package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;
import java.util.UUID;

@Node
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private UUID uuid;
    private String name;
    private String surname;
    private String nickname;
    private boolean isPublic;
    private boolean isLocked;
    private int followersNumber;
    private int followingNumber;
    private int tripsNumber;
    private String profilePictureUrl;
    private String profileUrl;
    private Set<String> languages;
    private Set<String> activities;

    @Relationship(type = "FOLLOWS")
    private Set<Follows> follows;

    @Relationship(type = "REACTS_TO_RELATION")
    private Set<EventRelation> relationReactions;

    @Relationship(type = "APPLIES_FOR_EVENT")
    private Set<Event> appliedEvents;

    @Relationship(type = "OWNS_EVENT")
    private Set<Event> ownedEvents;

    @Relationship(type = "IS_EVENT_MEMBER")
    private Set<Event> enteredEvents;

    @Relationship(type = "USER_NOTIFIES")
    private Notification generatedNotification;

    @Relationship(type = "REACTS_TO_COMMENT")
    private Set<Comment> reactedComments;

    @Relationship(type = "COMMENTS")
    private Set<Comment> comments;

    @Relationship(type = "CHATS")
    private Set<Chats> chats;

    @Relationship(type = "REQUEST_FOLLOW")
    private Set<User> requestedFollows;

    @Relationship(type = "IS_TAGGED")
    private Set<Post> taggedInPosts;

    @Relationship(type = "REACTS_TO_POST")
    private Set<Post> reactedPosts;

    @Relationship(type = "POSTS")
    private Set<Post> posts;

    @Relationship(type = "BELONGS_TO_GROUP")
    private Set<Group> groups;

    @Relationship(type = "ADMINISTRATE")
    private Set<Group> administratedGroups;

    @Relationship(type = "OWNS_GROUP")
    private Set<Group> ownedGroups;

    @Relationship(type = "APPLIES_FOR_GROUP")
    private Set<Group> appliedGroups;

}
