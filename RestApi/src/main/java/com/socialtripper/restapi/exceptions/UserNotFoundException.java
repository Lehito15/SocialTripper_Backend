package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.UUID;

/**
 * Wyjątek sygnalizujący, że nie znaleziono użytkownika.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucania serwer zwraca odpowiedź HTTP z kodem 404 (Not Found).
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with given uuid not found.")
public class UserNotFoundException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjątek w przypadku gdy użytkownik o wskazanym UUID nie istnieje.
     *
     * @param uuid globalny, unikalny identyfikator użytkownika w systemie
     */
    public UserNotFoundException(UUID uuid) {
        super("User with given uuid not found: " + uuid);
    }
}
