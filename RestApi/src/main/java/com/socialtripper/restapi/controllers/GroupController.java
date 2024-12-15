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

/**
 * Kontroler zarządzający żądaniami HTTP związanymi z grupami.
 */
@Controller
public class GroupController {
    /**
     * Serwis zarządzający operacjami związanymi z grupami.
     */
    private final GroupService groupService;

    /**
     * Konstruktor wstrzykujący serwis zarządzający operacjami związanymi z grupami.
     * @param groupService serwis zarządzający operacjami związanymi z grupami
     */
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Pobieranie grup.
     *
     * @return odpowiedź http z listą data transfer object grup
     */
    @GetMapping("/groups")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.findAllGroups());
    }

    /**
     * Pobranie grupy o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return odpowiedź http z data transfer object grupy lub błąd 404, gdy grupa nie istnieje
     */
    @GetMapping("/groups/{uuid}")
    public ResponseEntity<GroupDTO> getGroupByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.findGroupByUUID(uuid));
    }

    /**
     * Tworzenie grupy.
     *
     * @param groupDTO data transfer object grupy
     * @param icon plik ikony grupy
     * @return odpowiedź http z data transfer object nowo utworzonej grupy
     */
    @PostMapping("/groups")
    public ResponseEntity<GroupDTO> createGroup(@RequestPart GroupDTO groupDTO,
                                                @RequestPart(required = false) MultipartFile icon) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(groupService.createGroup(groupDTO, icon));
    }

    /**
     * Pobieranie grup użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z listą data transfer object grup
     */
    @GetMapping("/users/{uuid}/groups")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.findUserCurrentGroups(uuid));
    }

    /**
     * Dodanie użytkownika do grupy.
     *
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wiadomością o dodaniu użytkownika do grupy
     */
    @PostMapping("/groups/{group-uuid}/users/{user-uuid}")
    public ResponseEntity<UserJoinsGroupMessageDTO> addUserToGroup(@PathVariable("group-uuid") UUID groupUUID,
                                                                   @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(groupService.addUserToGroup(userUUID, groupUUID));
    }

    /**
     * Usunięcie użytkownika z grupy.
     *
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wiadomością o usunięciu użytkownika z grupy
     */
    @DeleteMapping("/groups/{group-uuid}/users/{user-uuid}")
    public ResponseEntity<UserLeavesGroupMessageDTO> removeUserFromGroup(@PathVariable("group-uuid") UUID groupUUID,
                                                                         @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(groupService.removeUserFromGroup(userUUID, groupUUID));
    }

    /**
     * Aktualizacja danych grupy.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @param groupDTO data transfer object grupy
     * @return odpowiedź http z data transfer object grupy
     */
    @PatchMapping("/groups/{uuid}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable UUID uuid, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(uuid, groupDTO));
    }

    /**
     * Pobranie informacji czy użytkownik jest członkiem grupy.
     *
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/users/{user-uuid}/groups/{group-uuid}/is-member")
    public ResponseEntity<Boolean> isUserInGroup(@PathVariable("user-uuid") UUID userUUID,
                                                 @PathVariable("group-uuid") UUID groupUUID) {
        return ResponseEntity.ok(groupService.isUserInGroup(userUUID, groupUUID));
    }


    /**
     * Wysłanie zapytania o dołączenie do grupy.
     *
     * @param userRequestGroup data transfer object zapytania o dołączenie do grupy
     * @return odpowiedź http z wiadomością o wysłąniu zapytania
     */
    @PostMapping("/groups/requests")
    public ResponseEntity<UserRequestGroupDTO> addUserRequestForGroup(@RequestBody UserRequestGroupDTO userRequestGroup) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                groupService.addUserRequestGroup(userRequestGroup.userUUID(),
                        userRequestGroup.groupUUID())
        );
    }

    /**
     * Usunięcie zapytania o dołączenie do grupy.
     *
     * @param userRequestGroup data transfer object zapytania o dołączenie do grupy
     * @return odpowiedź http z wiadomością o usunięciu zapytania
     */
    @DeleteMapping("/groups/requests")
    public ResponseEntity<UserRequestGroupDTO> removeUserRequestForGroup(@RequestBody UserRequestGroupDTO userRequestGroup) {
        return ResponseEntity.ok(groupService.removeUserRequestGroup(
                userRequestGroup.userUUID(),
                userRequestGroup.groupUUID()));
    }

    /**
     * Pobranie członków grupy.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return odpowiedź http z listą data transfer object częściowej informacji o koncie
     */
    @GetMapping("/groups/{uuid}/members")
    public ResponseEntity<List<AccountThumbnailDTO>> getGroupMembers(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.getGroupMembers(uuid));
    }

    /**
     * Pobranie informacji czy użytkownik wysłał zapytanie o dołączenie do grupy.
     *
     * @param groupUUID globalny, unikalny identyfikator grupy w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/groups/{group-uuid}/users/{user-uuid}/is-group-requested")
    public ResponseEntity<Boolean> isGroupRequested(@PathVariable("group-uuid") UUID groupUUID,
                                                    @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(groupService.isGroupRequestSent(userUUID, groupUUID));
    }

    /**
     * Aktualizacja ikony grupowej.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @param icon plik nowej ikony grupy
     * @return odpowiedź http z data transfer object multimedium
     */
    @PatchMapping("/groups/{uuid}/profile-picture")
    public ResponseEntity<MultimediaDTO> updateGroupPicture(@PathVariable UUID uuid,
                                                            @RequestPart MultipartFile icon) {
        return ResponseEntity.ok(groupService.updateGroupPicture(uuid, icon));
    }

    /**
     * Pobranie zapytań o dołącznie do grupy.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return odpowiedź http z listą data transfer object częściowej informacji o koncie
     */
    @GetMapping("/groups/{uuid}/requests")
    public ResponseEntity<List<AccountThumbnailDTO>> getGroupRequests(@PathVariable UUID uuid) {
        return ResponseEntity.ok(groupService.findGroupRequests(uuid));
    }
}
