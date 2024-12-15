package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.UUID;

/**
 * Wyjątek sygnalizujący, że nie znaleziono komentarza.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucania serwer zwraca odpowiedź HTTP z kodem 404 (Not Found).
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Comment with given uuid not found.")
public class CommentNotFoundException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjątek w przypadku gdy komentarz o wskazanym UUID nie istnieje.
     *
     * @param uuid globalny, unikalny identyfikator komentarza w systemie
     */
    public CommentNotFoundException(UUID uuid) {
        super("Comment with given uuid not found: " + uuid);
    }
}
