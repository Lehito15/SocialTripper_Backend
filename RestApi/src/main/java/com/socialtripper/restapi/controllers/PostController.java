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

/**
 * Kontroler zarządzający żądaniami HTTP związanymi z postami.
 */
@Controller
public class PostController {
    /**
     * Serwis zarządzający operacjami związanymi z postami.
     */
    private final PostService postService;

    /**
     * Konstruktor wstrzykujący serwis zarządzający operacjami związanymi z postami.
     * @param postService serwis zarządzający operacjami związanymi z postami
     */
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Pobranie postów.
     *
     * @return odpowiedź http z listą data transfer object postów
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    /**
     * Pobranie postu o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return odpowiedź http z data transfer object postu
     */
    @GetMapping("/posts/{uuid}")
    public ResponseEntity<PostDTO> getPostByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostByUUID(uuid));
    }

    /**
     * Pobranie postów użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z listą data transfer object postów
     */
    @GetMapping("/users/{uuid}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUserUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostsByUserUUID(uuid));
    }

    /**
     * Pobranie postów personalnych użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z listą data transfer object postów
     */
    @GetMapping("/users/{uuid}/personal-posts")
    public ResponseEntity<List<PostDTO>> getPersonalPostsByUserUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPersonalPostsByUserUUID(uuid));
    }

    /**
     * Pobranie postów grupowych.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     * @return odpowiedź http z listą data transfer object postów
     */
    @GetMapping("/groups/{uuid}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByGroupUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostsByGroupUUID(uuid));
    }

    /**
     * Pobranie postów w wydarzeniu.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     * @return odpowiedź http z listą data transfer object postów
     */
    @GetMapping("/events/{uuid}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByEventUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findPostsByEventUUID(uuid));
    }

    /**
     * Tworzenie nowego postu.
     *
     * @param postDTO data transfer object postu
     * @param multimedia zbiór plików multimedialnych dołączonych do postu
     * @return odpowiedź http z data transfer object postu
     */
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPersonalPost(@RequestPart PostDTO postDTO,
                                                      @RequestPart(required = false) MultipartFile[] multimedia) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(postService.saveUserPost(postDTO, multimedia));
    }

    /**
     * Tworzenie nowego postu grupowego.
     *
     * @param postDTO data transfer object postu grupowego
     * @param multimedia zbiór plików multimedialnych dołączonych do postu
     * @return odpowiedź http z data transfer object postu grupowego
     */
    @PostMapping("/posts/group-post")
    public ResponseEntity<GroupPostDTO> createGroupPost(@RequestPart GroupPostDTO postDTO,
                                                        @RequestPart(required = false) MultipartFile[] multimedia) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                                .body(postService.saveGroupPost(postDTO, multimedia));
    }

    /**
     * Tworzenie nowego postu w wydarzeniu.
     *
     * @param postDTO data transfer object postu w wydarzeniu
     * @param multimedia zbiór plików multimedialnych dołączonych do postu
     * @return odpowiedź http z data transfer object postu w wydarzeniu
     */
    @PostMapping("/posts/event-post")
    public ResponseEntity<EventPostDTO> createEventPost(@RequestPart EventPostDTO postDTO,
                                                        @RequestPart(required = false) MultipartFile[] multimedia) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED)
                .body(postService.saveEventPost(postDTO, multimedia));
    }

    /**
     * Dezaktywacja postu.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return odpowiedź http z wiadomością o dezaktywacji postu
     */
    @DeleteMapping("/posts/{uuid}/expire-post")
    public ResponseEntity<PostExpiredMessageDTO> expirePostByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.expirePostByUUID(uuid));
    }

    /**
     * Dezaktywacja postów użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wiadomością o dezaktywacji postów użytkownika
     */
    @DeleteMapping("/posts/{uuid}/expire-user-posts")
    public ResponseEntity<UserPostsExpiredMessageDTO> expireUserPostsByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.expirePostsByUserUUID(uuid));
    }

    /**
     * Zablokowanie postów użytkownika.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wiadomością o zablokowaniu postów użytkownika
     */
    @DeleteMapping("/posts/{uuid}/lock-user-posts")
    public ResponseEntity<UserPostsLockedMessageDTO> lockUserPostsByUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.lockPostByUUID(uuid));
    }

    /**
     * Aktualizacja danych postu.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @param postDTO data transfer object postu
     * @return odpowiedź http z data transfer object postu
     */
    @PatchMapping("/posts/{uuid}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable UUID uuid, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(uuid, postDTO));
    }

    /**
     * Dodanie reakcji użytkownika do postu
     * @param postReaction data transfer object reakcji na post
     * @return odpowiedź http z wiadomością o reakcji na post
     */
    @PostMapping("/posts/react")
    public ResponseEntity<UserReactionToPostMessageDTO> addUserReactionToPost(@RequestBody PostReactionDTO postReaction) {
        return ResponseEntity.ok(postService.addUserReactionToPost(
                postReaction.userUUID(),
                postReaction.postUUID()));
    }

    /**
     * Usunięcie reakcji użytkownika do postu
     * @param postReaction data transfer object reakcji na post
     * @return odpowiedź http z wiadomością o usunięciu reakcji na post
     */
    @DeleteMapping("/posts/react")
    public ResponseEntity<UserReactionToPostMessageDTO> removeUserReactionFromPost(@RequestBody PostReactionDTO postReaction) {
        return ResponseEntity.status(HttpURLConnection.HTTP_NO_CONTENT).body(
                postService.removeUserReactionToPost(
                        postReaction.userUUID(),
                        postReaction.postUUID())
        );
    }

    /**
     * Pobranie informacji czy użytkownik zareagował na post.
     *
     * @param postUUID globalny, unikalny identyfikator postu w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/posts/{post-uuid}/user/{user-uuid}/did-react")
    public ResponseEntity<Boolean> didUserReactToPost(@PathVariable("post-uuid") UUID postUUID,
                                                      @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(postService.didUserReactToPost(userUUID, postUUID));
    }

    /**
     * Pobranie postów obserwowanych użytkowników.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z listą data transfer object postów
     */
    @GetMapping("/users/{uuid}/followed-users-posts")
    public ResponseEntity<List<PostDTO>> getFollowedUsersPostsByUserUUID(@PathVariable UUID uuid) {
        return ResponseEntity.ok(postService.findFollowedUsersPosts(uuid));
    }

    /**
     * Pobranie trendujących postów.
     *
     * @param numberOfPosts liczba postów do pobrania
     * @param daysBound uwzględniany zakres dni wstecz
     * @return odpowiedź http z listą data transfer object postów
     */
    @GetMapping("/posts/trending")
    public ResponseEntity<List<PostDTO>> getTrendingPosts(@RequestParam(value = "numberOfPosts", required = false) int numberOfPosts,
                                                          @RequestParam(value = "daysBound", required = false) int daysBound) {
        if (numberOfPosts <= 0) numberOfPosts = 10;
        if (daysBound <= 0) daysBound = 7;
        return ResponseEntity.ok(postService.findTrendingPosts(numberOfPosts, daysBound));
    }

}
