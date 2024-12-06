package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.FollowDTO;
import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.dto.messages.UserEndsFollowingMessageDTO;
import com.socialtripper.restapi.dto.messages.UserStartsFollowingMessageDTO;
import com.socialtripper.restapi.dto.requests.UserRequestFollowDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{uuid}")
    public ResponseEntity<UserDTO> getUserByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.findUserByUUID(uuid));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestPart UserDTO userDTO,
                                              @RequestPart(required = false) MultipartFile profilePicture) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(userService.createUser(userDTO, profilePicture));
    }

    @PatchMapping("/users/{uuid}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID uuid,
                                              @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(uuid, userDTO));
    }

    @PostMapping("/users/{uuid}/languages")
    public ResponseEntity<UserLanguageDTO> setUserLanguage(@PathVariable UUID uuid,
                                                           @RequestBody UserLanguageDTO userLanguageDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(userService.setUserLanguage(uuid, userLanguageDTO));
    }

    @PostMapping("/users/{uuid}/activities")
    public ResponseEntity<UserActivityDTO> setUserActivity(@PathVariable UUID uuid,
                                                           @RequestBody UserActivityDTO userActivityDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(userService.setUserActivity(uuid, userActivityDTO));
    }

    @PostMapping("/users/follow-request")
    public ResponseEntity<UserRequestFollowDTO> addFollowRequest(@RequestBody FollowDTO followRequest) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                userService.addFollowRequest(followRequest)
        );
    }

    @DeleteMapping("/users/follow-request")
    public ResponseEntity<UserRequestFollowDTO> removeFollowRequest(@RequestBody FollowDTO followRequest) {
        return ResponseEntity.ok(userService.removeFollowRequest(followRequest));
    }

    @PostMapping("/users/follow")
    public ResponseEntity<UserStartsFollowingMessageDTO> addFollow(@RequestBody FollowDTO follow) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                userService.followUser(follow)
        );
    }

    @DeleteMapping("/users/follow")
    public ResponseEntity<UserEndsFollowingMessageDTO> removeFollow(@RequestBody FollowDTO follow) {
        return ResponseEntity.status(HttpURLConnection.HTTP_NO_CONTENT).body(
                userService.unfollowUser(follow)
        );
    }

    @GetMapping("/users/is-following")
    public ResponseEntity<Boolean> isFollowing(@RequestParam UUID followerUUID,
                                               @RequestParam UUID followedUUID) {
        return ResponseEntity.ok(userService.isFollowingUser(followerUUID, followedUUID));
    }

    @GetMapping("/users/{uuid}/follows")
    public ResponseEntity<List<AccountThumbnailDTO>> getFollowedAccounts(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getFollowedAccounts(uuid));
    }

    @GetMapping("/users/{uuid}/followed-by")
    public ResponseEntity<List<AccountThumbnailDTO>> getFollowingAccounts(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getFollowingAccounts(uuid));
    }

    @GetMapping("/users/{uuid}/follow-requests")
    public ResponseEntity<List<AccountThumbnailDTO>> getFollowRequests(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getUserFollowRequests(uuid));
    }

    @GetMapping("/users/is-follow-requested")
    public ResponseEntity<Boolean> isFollowRequested(@RequestParam UUID followerUUID,
                                                     @RequestParam UUID followedUUID) {
        return ResponseEntity.ok(userService.isFollowRequestSent(followerUUID, followedUUID));
    }

    @GetMapping("/users/{uuid}/recommended-accounts")
    public ResponseEntity<List<AccountThumbnailDTO>> getRecommendedAccounts(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getRecommendedUsers(uuid));
    }
}
