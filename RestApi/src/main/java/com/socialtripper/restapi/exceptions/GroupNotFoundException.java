package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Wyjątek sygnalizujący, że nie znaleziono grupy.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucania serwer zwraca odpowiedź HTTP z kodem 404 (Not Found).
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GroupNotFoundException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjątek w przypadku gdy grupa o wskazanym UUID nie istnieje.
     *
     * @param uuid globalny, unikalny identyfikator grupy w systemie
     */
    public GroupNotFoundException(UUID uuid) {
        super("Group with given uuid not found: " + uuid);
    }
}
