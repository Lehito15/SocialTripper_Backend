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

/**
 * Kontroler zarządzający żądaniami HTTP związanymi z użytkownikami.
 */
@Controller
public class UserController {
    /**
     * Serwis zarządzający operacjami związanymi z użytkownikami.
     */
    private final UserService userService;

    /**
     * Konstruktor wstrzykujący serwis zarządzający operacjami związanymi z użytkownikami.
     * @param userService serwis zarządzający operacjami związanymi z użytkownikami
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Pobranie użytkownika o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return odpowiedź http z data transfer object użytkownika
     */
    @GetMapping("/users/{uuid}")
    public ResponseEntity<UserDTO> getUserByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.findUserByUUID(uuid));
    }

    /**
     * Tworzenie nowego użytkownika.
     *
     * @param userDTO data transfer object użytkownika
     * @param profilePicture plik zdjęcia profilowego
     * @return odpowiedź http z data transfer object użytkownika
     */
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestPart UserDTO userDTO,
                                              @RequestPart(required = false) MultipartFile profilePicture) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(userService.createUser(userDTO, profilePicture));
    }

    /**
     * Aktualizacja danych użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userDTO data transfer object użytkownika
     * @return odpowiedź http z data transfer object użytkownika
     */
    @PatchMapping("/users/{uuid}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID uuid,
                                              @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(uuid, userDTO));
    }

    /**
     * Aktualizacja języka użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userLanguageDTO data transfer object języka użytkownika
     * @return odpowiedź http z data transfer object języka użytkownika
     */
    @PostMapping("/users/{uuid}/languages")
    public ResponseEntity<UserLanguageDTO> setUserLanguage(@PathVariable UUID uuid,
                                                           @RequestBody UserLanguageDTO userLanguageDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(userService.setUserLanguage(uuid, userLanguageDTO));
    }

    /**
     * Aktualizacja aktywności użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @param userActivityDTO data transfer object aktywności użytkownika
     * @return odpowiedź http z data transfer object aktywności użytkownika
     */
    @PostMapping("/users/{uuid}/activities")
    public ResponseEntity<UserActivityDTO> setUserActivity(@PathVariable UUID uuid,
                                                           @RequestBody UserActivityDTO userActivityDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(userService.setUserActivity(uuid, userActivityDTO));
    }

    /**
     * Dodanie zapytania o obserwację użytkownika.
     *
     * @param followRequest data transfer object obserwacji
     * @return odpowiedź http z zapytaniem o obserwację użytkownika
     */
    @PostMapping("/users/follow-request")
    public ResponseEntity<UserRequestFollowDTO> addFollowRequest(@RequestBody FollowDTO followRequest) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                userService.addFollowRequest(followRequest)
        );
    }

    /**
     * Usunięcie zapytania o obserwację użytkownika.
     *
     * @param followRequest data transfer object obserwacji
     * @return odpowiedź http z usuniętym zapytaniem o obserwację użytkownika
     */
    @DeleteMapping("/users/follow-request")
    public ResponseEntity<UserRequestFollowDTO> removeFollowRequest(@RequestBody FollowDTO followRequest) {
        return ResponseEntity.ok(userService.removeFollowRequest(followRequest));
    }

    /**
     * Dodanie obserwacji użytkownika.
     *
     * @param follow data transfer object obserwacji
     * @return odpowiedź http z obserwają użytkownika
     */
    @PostMapping("/users/follow")
    public ResponseEntity<UserStartsFollowingMessageDTO> addFollow(@RequestBody FollowDTO follow) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                userService.followUser(follow)
        );
    }

    /**
     * Usunięcie obserwacji użytkownika.
     *
     * @param follow data transfer object obserwacji
     * @return odpowiedź http z usunięciem obserwaji użytkownika
     */
    @DeleteMapping("/users/follow")
    public ResponseEntity<UserEndsFollowingMessageDTO> removeFollow(@RequestBody FollowDTO follow) {
        return ResponseEntity.status(HttpURLConnection.HTTP_NO_CONTENT).body(
                userService.unfollowUser(follow)
        );
    }

    /**
     * Pobranie informacji czy użytkownik obserwuje innego użytkownika.
     *
     * @param followerUUID globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUUID globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/users/is-following")
    public ResponseEntity<Boolean> isFollowing(@RequestParam UUID followerUUID,
                                               @RequestParam UUID followedUUID) {
        return ResponseEntity.ok(userService.isFollowingUser(followerUUID, followedUUID));
    }

    /**
     * Pobranie obserwowanych kont.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return odpowiedź http z listą data transfer object częściowej informacji o koncie
     */
    @GetMapping("/users/{uuid}/follows")
    public ResponseEntity<List<AccountThumbnailDTO>> getFollowedAccounts(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getFollowedAccounts(uuid));
    }

    /**
     * Pobranie obserwujących kont.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return odpowiedź http z listą data transfer object częściowej informacji o koncie
     */
    @GetMapping("/users/{uuid}/followed-by")
    public ResponseEntity<List<AccountThumbnailDTO>> getFollowingAccounts(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getFollowingAccounts(uuid));
    }

    /**
     * Pobranie zapytań o obserwowanie.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return odpowiedź http z listą data transfer object częściowych informacji o koncie
     */
    @GetMapping("/users/{uuid}/follow-requests")
    public ResponseEntity<List<AccountThumbnailDTO>> getFollowRequests(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getUserFollowRequests(uuid));
    }

    /**
     * Pobranie informacji czy użytkownik wysłał zapytanie o obserwację.
     *
     * @param followerUUID globalny, unikalny identyfikator użytkownika obserwującego w systemie
     * @param followedUUID globalny, unikalny identyfikator użytkownika obserwowanego w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/users/is-follow-requested")
    public ResponseEntity<Boolean> isFollowRequested(@RequestParam UUID followerUUID,
                                                     @RequestParam UUID followedUUID) {
        return ResponseEntity.ok(userService.isFollowRequestSent(followerUUID, followedUUID));
    }

    /**
     * Pobranie rekomendowanych użytkowników.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     * @return odpowiedź http z listą data transfer object częściowej informacji o koncie
     */
    @GetMapping("/users/{uuid}/recommended-accounts")
    public ResponseEntity<List<AccountThumbnailDTO>> getRecommendedAccounts(@PathVariable UUID uuid) {
        return ResponseEntity.ok(userService.getRecommendedUsers(uuid));
    }
}
