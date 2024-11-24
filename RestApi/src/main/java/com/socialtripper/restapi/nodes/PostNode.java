package com.socialtripper.restapi.nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node("POST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostNode {
    @Id
    private String id;

    private UUID uuid;
    private String content;
    private LocalDateTime postTime;
    private Set<String> multimediaUrls;

    @Relationship(type = "POSTED_IN_GROUP")
    private GroupNode group;

    @Relationship(type = "POSTED_IN_EVENT")
    private EventNode event;

    @Relationship(type = "REACTION_TO_POST_BY")
    private Set<UserNode> reactingUsers;

    @Relationship(type = "POST_COMMENTED")
    private Set<CommentNode> comments;

    @Relationship(type = "POSTED_BY")
    private UserNode author;

    public PostNode(UUID uuid, String content, LocalDateTime postTime) {
        this.id = UUID.randomUUID().toString();
        this.uuid = uuid;
        this.content = content;
        this.postTime = postTime;
        this.multimediaUrls = new HashSet<>();
        this.reactingUsers = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public int getCommentsNumber() {
        if (comments == null) return 0;
        return comments.size();
    }

    public int getReactionsNumber() {
        if (reactingUsers == null) return 0;
        return reactingUsers.size();
    }
}
