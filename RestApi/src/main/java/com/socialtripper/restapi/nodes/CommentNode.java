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

@Node("COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentNode {
    @Id
    private String id;

    private UUID uuid;
    private String content;
    private LocalDateTime commentDate;

    @Relationship(type = "REACTION_TO_COMMENT_BY")
    private Set<UserNode> reactingUsers;

    @Relationship(type = "COMMENTED_BY")
    private UserNode commentAuthor;

    @Relationship(type = "POST_COMMENTED", direction = Relationship.Direction.INCOMING)
    private PostNode commentedPost;

    public int getReactionsNumber() {
        return reactingUsers.size();
    }

    public CommentNode(String content, LocalDateTime commentDate,
                       UserNode commentAuthor, PostNode commentedPost) {
        this.id = UUID.randomUUID().toString();
        this.uuid = UUID.randomUUID();
        this.content = content;
        this.commentDate = commentDate;
        this.commentAuthor = commentAuthor;
        this.reactingUsers = new HashSet<>();
        this.commentedPost = commentedPost;
    }
}
