package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.UserActivityDTO;
import com.socialtripper.restapi.dto.entities.UserDTO;
import com.socialtripper.restapi.dto.entities.UserLanguageDTO;
import com.socialtripper.restapi.dto.requests.UserRequestEventDTO;
import com.socialtripper.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.HttpURLConnection;
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
}
