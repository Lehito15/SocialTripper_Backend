package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Wyjątek sygnalizujący, że nie znaleziono postu.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucania serwer zwraca odpowiedź HTTP z kodem 404 (Not Found).
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post with given uuid not found.")
public class PostNotFoundException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjątek w przypadku gdy post o wskazanym UUID nie istnieje.
     *
     * @param uuid globalny, unikalny identyfikator postu w systemie
     */
    public PostNotFoundException(UUID uuid) {super("Post with given uuid not found: " + uuid);}
}
