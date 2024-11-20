package com.socialtripper.restapi.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;

@Node
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String content;
    private String mediaUrl;
    private int reactionsNumber;
    private LocalDateTime commentDate;

    @Relationship(type = "REFERS_TO_COMMENT")
    private Comment comment;

    @Relationship(type = "REFERS_TO_POST")
    private PostNode post;

    @Relationship(type = "COMMENT_NOTIFIES")
    private Notification generatedNotification;
}
