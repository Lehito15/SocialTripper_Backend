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

/**
 * Kontroler zarządzający żadaniami HTTP związanymi z komentarzami dotyczącymi postów.
 */
@Controller
public class CommentController {
    /**
     * Serwis zarządzający operacjami związanymi z komentarzami.
     */
    private final CommentService commentService;

    /**
     * Konstruktor wstrzykujący serwis zarządzający operacjami związanymi z komentarzami.
     *
     * @param commentService serwis zarządzający operacjami związanymi z komertarzami
     */
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Pobieranie danych postu.
     *
     * @param uuid globalny, unikalny identyfikator komentarza w systemie
     * @return odpowiedź http z data transfer object komentarza lub błąd 404, gdy komentarz nie istnieje
     */
    @GetMapping("/comments/{uuid}")
    public ResponseEntity<CommentDTO> getCommentByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(commentService.findCommentByUUID(uuid));
    }

    /**
     * Tworzenie nowego komentarza.
     *
     * @param postUUID globalny, unikalny identyfikator postu w systemie, którego dotyczy komentarz
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie, autora komentarza
     * @param commentDTO data transfer object komentarza
     * @return odpowiedź http z data transfer object komentarza
     */
    @PostMapping("/posts/{post-uuid}/users/{user-uuid}/comment")
    public ResponseEntity<CommentDTO> createPostComment(@PathVariable("post-uuid") UUID postUUID,
                                                        @PathVariable("user-uuid") UUID userUUID,
                                                        @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.status(HttpURLConnection.HTTP_CREATED).body(
                commentService.createPostComment(userUUID, postUUID, commentDTO));
    }

    /**
     * Pobieranie komentarzy dotyczących postu.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     * @return odpowiedź http z listą data transfer object komentarza
     */
    @GetMapping("/posts/{uuid}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable UUID uuid) {
        return ResponseEntity.ok(commentService.findPostComments(uuid));
    }

    /**
     * Dodanie reakcji użytkownika na komentarz.
     *
     * @param commentUUID globalny, unikalny identyfikator komentarza w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wiadomością o reakcji na komentarz
     */
    @PostMapping("/comments/{comment-uuid}/users/{user-uuid}/react")
    public ResponseEntity<UserReactionToCommentMessageDTO> addUserReactionToComment(@PathVariable("comment-uuid") UUID commentUUID,
                                                                                    @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(commentService.addUserReactionToComment(userUUID, commentUUID));
    }

    /**
     * Usunięcie reakcji użytkownika na komentarz.
     *
     * @param commentUUID globalny, unikalny identyfikator komentarza w systemie
     * @param userUUID globalny, unikalny identyfikator konta użytkownika w systemie
     * @return odpowiedź http z wiadomością o rekacji na komentarz
     */
    @DeleteMapping("/comments/{comment-uuid}/users/{user-uuid}/react")
    public ResponseEntity<UserReactionToCommentMessageDTO> removeUserReactionToComment(@PathVariable("comment-uuid") UUID commentUUID,
                                                                                       @PathVariable("user-uuid") UUID userUUID) {
        return ResponseEntity.ok(commentService.removeUserReactionToComment(userUUID, commentUUID));
    }

    /**
     * Pobranie informacji czy użytkownik zareagował na komentarz.
     *
     * @param userUuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param commentUUID globalny, unikalny identyfikator komentarza w systemie
     * @return odpowiedź http z wartością logiczną
     */
    @GetMapping("/comments/{comment-uuid}/users/{user-uuid}/did-react")
    public ResponseEntity<Boolean> didUserReactToComment(@PathVariable("user-uuid") UUID userUuid,
                                                         @PathVariable("comment-uuid") UUID commentUUID) {
        return ResponseEntity.ok(commentService.didUserReactToComment(userUuid, commentUUID));
    }
}
