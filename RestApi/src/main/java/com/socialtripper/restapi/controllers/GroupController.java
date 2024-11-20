package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsGroupMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesGroupMessageDTO;
import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.UUID;

@Controller
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.findAllGroups());
    }

    @GetMapping("/groups/{uuid}")
    public ResponseEntity<GroupDTO> getGroupByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.findGroupByUUID(uuid));
    }

    @PostMapping("/groups")
    public ResponseEntity<GroupDTO> createGroup(@RequestPart GroupDTO groupDTO,
                                                @RequestPart(required = false) MultipartFile icon) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(groupService.createGroup(groupDTO, icon));
    }

    @GetMapping("/users/{uuid}/groups")
    public ResponseEntity<List<GroupThumbnailDTO>> getUserGroups(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.findUserCurrentGroups(uuid));
    }

    @PostMapping("/groups/{group-uuid}/users/{user-uuid}")
    public ResponseEntity<UserJoinsGroupMessageDTO> addUserToGroup(@PathVariable("group-uuid") UUID groupUUID,
                                                                   @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(groupService.addUserToGroup(groupUUID, userUUID));
    }

    @DeleteMapping("/groups/{group-uuid}/users/{user-uuid}")
    public ResponseEntity<UserLeavesGroupMessageDTO> removeUserFromGroup(@PathVariable("group-uuid") UUID groupUUID,
                                                                         @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(groupUUID, userUUID));
    }

    @PatchMapping("/groups/{uuid}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable UUID uuid, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(uuid, groupDTO));
    }
}
