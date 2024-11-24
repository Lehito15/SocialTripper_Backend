package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.CommentDTO;
import com.socialtripper.restapi.dto.messages.UserReactionToCommentMessageDTO;
import com.socialtripper.restapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

@Controller
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{uuid}")
    public ResponseEntity<CommentDTO> getCommentByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(commentService.findCommentByUUID(uuid));
    }

    @PostMapping("/posts/{post-uuid}/users/{user-uuid}/comment")
    public ResponseEntity<CommentDTO> createPostComment(@PathVariable("post-uuid") UUID postUUID,
                                                        @PathVariable("user-uuid") UUID userUUID,
                                                        @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                commentService.createPostComment(userUUID, postUUID, commentDTO));
    }

    @GetMapping("/posts/{uuid}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable UUID uuid) {
        return ResponseEntity.ok(commentService.findPostComments(uuid));
    }

    @PostMapping("/comments/{comment-uuid}/users/{user-uuid}/react")
    public ResponseEntity<UserReactionToCommentMessageDTO> addUserReactionToComment(@PathVariable("comment-uuid") UUID commentUUID,
                                                                                    @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(commentService.addUserReactionToComment(userUUID, commentUUID));
    }

    @DeleteMapping("/comments/{comment-uuid}/users/{user-uuid}/react")
    public ResponseEntity<UserReactionToCommentMessageDTO> removeUserReactionToComment(@PathVariable("comment-uuid") UUID commentUUID,
                                                                                       @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(commentService.removeUserReactionToComment(userUUID, commentUUID));
    }
}
