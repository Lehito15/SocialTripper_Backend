package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

/**
 * Wyjątek sygnalizujący, że nie znaleziono wydarzenia.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucania serwer zwraca odpowiedź HTTP z kodem 404 (Not Found).
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event with given uuid not found.")
public class EventNotFoundException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjątek w przypadku gdy wydarzenie o wskazanym UUID nie istnieje.
     *
     * @param uuid globalny, unikalny identyfikator wydarzenia w systemie
     */
    public EventNotFoundException(UUID uuid) {
        super("Event with given uuid not found: " + uuid);
    }
}
