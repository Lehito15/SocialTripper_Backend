package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.EventPostDTO;
import com.socialtripper.restapi.dto.entities.GroupPostDTO;
import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.dto.messages.PostExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsExpiredMessageDTO;
import com.socialtripper.restapi.dto.messages.UserPostsLockedMessageDTO;
import com.socialtripper.restapi.dto.messages.UserReactionToPostMessageDTO;
import com.socialtripper.restapi.dto.requests.PostReactionDTO;
import com.socialtripper.restapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/posts/{uuid}")
    public ResponseEntity<PostDTO> getPostByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostByUUID(uuid));
    }

    @GetMapping("/users/{uuid}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUserUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostsByUserUUID(uuid));
    }

    @GetMapping("/users/{uuid}/personal-posts")
    public ResponseEntity<List<PostDTO>> getPersonalPostsByUserUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPersonalPostsByUserUUID(uuid));
    }

    @GetMapping("/groups/{uuid}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByGroupUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostsByGroupUUID(uuid));
    }

    @GetMapping("/events/{uuid}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByEventUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostsByEventUUID(uuid));
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPersonalPost(@RequestPart PostDTO postDTO,
                                                      @RequestPart(required = false) MultipartFile[] multimedia) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(postService.saveUserPost(postDTO, multimedia));
    }

    @PostMapping("/posts/group-post")
    public ResponseEntity<GroupPostDTO> createGroupPost(@RequestPart GroupPostDTO postDTO,
                                                        @RequestPart(required = false) MultipartFile[] multimedia) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(postService.saveGroupPost(postDTO, multimedia));
    }

    @PostMapping("/posts/event-post")
    public ResponseEntity<EventPostDTO> createEventPost(@RequestPart EventPostDTO postDTO,
                                                        @RequestPart(required = false) MultipartFile[] multimedia) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                .body(postService.saveEventPost(postDTO, multimedia));
    }

    @DeleteMapping("/posts/{uuid}/expire-post")
    public ResponseEntity<PostExpiredMessageDTO> expirePostByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.expirePostByUUID(uuid));
    }

    @DeleteMapping("/posts/{uuid}/expire-user-posts")
    public ResponseEntity<UserPostsExpiredMessageDTO> expireUserPostsByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.expirePostsByUserUUID(uuid));
    }

    @DeleteMapping("/posts/{uuid}/lock-user-posts")
    public ResponseEntity<UserPostsLockedMessageDTO> lockUserPostsByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.lockPostByUUID(uuid));
    }

    @PatchMapping("/posts/{uuid}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable UUID uuid, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(uuid, postDTO));
    }

    @PostMapping("/posts/react")
    public ResponseEntity<UserReactionToPostMessageDTO> addUserReactionToPost(@RequestBody PostReactionDTO postReaction) {
        return ResponseEntity.ok(postService.addUserReactionToPost(
                postReaction.userUUID(),
                postReaction.postUUID()));
    }

    @DeleteMapping("/posts/react")
    public ResponseEntity<UserReactionToPostMessageDTO> removeUserReactionFromPost(@RequestBody PostReactionDTO postReaction) {
        return ResponseEntity.status(HttpURLConnection.HTTP_NO_CONTENT).body(
                postService.removeUserReactionToPost(
                        postReaction.userUUID(),
                        postReaction.postUUID())
        );
    }

    @GetMapping("/posts/{post-uuid}/user/{user-uuid}/did-react")
    public ResponseEntity<Boolean> didUserReactToPost(@PathVariable("post-uuid") UUID postUUID,
                                                      @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(postService.didUserReactToPost(userUUID, postUUID));
    }

    @GetMapping("/users/{uuid}/followed-users-posts")
    public ResponseEntity<List<PostDTO>> getFollowedUsersPostsByUserUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findFollowedUsersPosts(uuid));
    }

}
