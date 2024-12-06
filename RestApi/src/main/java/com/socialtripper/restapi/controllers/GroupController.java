package com.socialtripper.restapi.controllers;

import com.socialtripper.restapi.dto.entities.GroupDTO;
import com.socialtripper.restapi.dto.messages.UserJoinsGroupMessageDTO;
import com.socialtripper.restapi.dto.messages.UserLeavesGroupMessageDTO;
import com.socialtripper.restapi.dto.requests.UserRequestGroupDTO;
import com.socialtripper.restapi.dto.thumbnails.AccountThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.GroupThumbnailDTO;
import com.socialtripper.restapi.dto.thumbnails.MultimediaDTO;
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
        return ResponseEntity.ok(groupService.addUserToGroup(userUUID, groupUUID));
    }

    @DeleteMapping("/groups/{group-uuid}/users/{user-uuid}")
    public ResponseEntity<UserLeavesGroupMessageDTO> removeUserFromGroup(@PathVariable("group-uuid") UUID groupUUID,
                                                                         @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(userUUID, groupUUID));
    }

    @PatchMapping("/groups/{uuid}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable UUID uuid, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(uuid, groupDTO));
    }

    @GetMapping("/users/{user-uuid}/groups/{group-uuid}/is-member")
    public ResponseEntity<Boolean> isUserInGroup(@PathVariable("user-uuid") UUID userUUID,
                                                 @PathVariable("group-uuid") UUID groupUUID) {
        return ResponseEntity.ok(groupService.isUserInGroup(userUUID, groupUUID));
    }

    @PostMapping("/groups/requests")
    public ResponseEntity<UserRequestGroupDTO> addUserRequestForGroup(@RequestBody UserRequestGroupDTO userRequestGroup) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                groupService.addUserRequestGroup(userRequestGroup.userUUID(),
                        userRequestGroup.groupUUID())
        );
    }

    @DeleteMapping("/groups/requests")
    public ResponseEntity<UserRequestGroupDTO> removeUserRequestForGroup(@RequestBody UserRequestGroupDTO userRequestGroup) {
        return ResponseEntity.ok(groupService.removeUserRequestGroup(
                userRequestGroup.userUUID(),
                userRequestGroup.groupUUID()));
    }

    @GetMapping("/groups/{uuid}/members")
    public ResponseEntity<List<AccountThumbnailDTO>> getGroupMembers(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.getGroupMembers(uuid));
    }

    @GetMapping("/groups/{group-uuid}/users/{user-uuid}/is-group-requested")
    public ResponseEntity<Boolean> isGroupRequested(@PathVariable("group-uuid") UUID groupUUID,
                                                    @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(groupService.isGroupRequestSent(userUUID, groupUUID));
    }

    @PatchMapping("/groups/{uuid}/profile-picture")
    public ResponseEntity<MultimediaDTO> updateGroupPicture(@PathVariable UUID uuid,
                                                            @RequestPart MultipartFile icon) {
        return ResponseEntity.ok(groupService.updateGroupPicture(uuid, icon));
    }
}
