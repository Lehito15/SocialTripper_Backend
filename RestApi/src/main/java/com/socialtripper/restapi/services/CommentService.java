package com.socialtripper.restapi.services;

import com.socialtripper.restapi.dto.entities.CommentDTO;
import com.socialtripper.restapi.dto.messages.UserReactionToCommentMessageDTO;
import com.socialtripper.restapi.nodes.CommentNode;
import java.util.List;
import java.util.UUID;

/**
 * Serwis obsługujący operacje wykonywane na węzłach komentarzy do postów.
 */
public interface CommentService {
    /**
     * Metoda znajdująca węzeł postu o wskazanym UUID.
     *
     * @param uuid globalny, unikalny identyfikator komentarza w systemie
     * @return węzeł komentarza
     */
    CommentNode findCommentNodeByUUID(UUID uuid);

    /**
     * Metoda zwracająca data transfer object komentarza.
     *
     * @param uuid uuid globalny, unikalny identyfikator komentarza w systemie
     * @return data transfer object komentarza
     */
    CommentDTO findCommentByUUID(UUID uuid);

    /**
     * Metoda tworząca komentarz do postu.
     *
     * @param userUUID uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param postUUID uuid globalny, unikalny identyfikator postu w systemie
     * @param commentDTO data transfer object komentarza
     * @return data transfer object nowo dodanego komentarza
     */
    CommentDTO createPostComment(UUID userUUID, UUID postUUID, CommentDTO commentDTO);

    /**
     * Metoda tworząca relację reakcji na komentarz użytkownika.
     *
     * @param userUUID uuid globalny, unikalny identyfikator konta użytkownika komentującego w systemie
     * @param commentUUID uuid globalny, unikalny identyfikator komentarza w systemie
     * @return wiadomość o dodaniu reakcji
     */
    UserReactionToCommentMessageDTO addUserReactionToComment(UUID userUUID, UUID commentUUID);

    /**
     * Metoda usuwająca relację reakcji na komentarz użytkownika.
     *
     * @param userUUID uuid globalny, unikalny identyfikator użytkownika komentującego w systemie
     * @param commentUUID uuid globalny, unikalny identyfikator komentarza w systemie
     * @return wiadomość o usunięciu reakcji
     */
    UserReactionToCommentMessageDTO removeUserReactionToComment(UUID userUUID, UUID commentUUID);

    /**
     * Lista komentarzy dotyczących postu.
     *
     * @param postUUID uuid globalny, unikalny identyfikator postu w systemie
     * @return lista data transfer object komentarzy dotyczących postu
     */
    List<CommentDTO> findPostComments(UUID postUUID);

    /**
     * Metoda zwracająca wartość logiczną informującą czy użytkownik zareagował na komentarz.
     *
     * @param userUUID uuid globalny, unikalny identyfikator konta użytkownika w systemie
     * @param commentUUID uuid globalny, unikalny identyfikator komentarza w systemie
     * @return wartość logiczna informująca czy użytkownika zareagował na komentarz
     */
    Boolean didUserReactToComment(UUID userUUID, UUID commentUUID);
}
