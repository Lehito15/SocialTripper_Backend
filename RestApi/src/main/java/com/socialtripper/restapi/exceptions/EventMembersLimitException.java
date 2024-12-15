package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wyjątek sygnalizujący, że nie można przypisać więcej użytkowników do wydarzenia.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucanie serwer zwraca odpowiedź HTTP z kodem 409 (Conflict).
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Limit of members in event has been reached.")
public class EventMembersLimitException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjątek z wiadomością informującą o konflikcie.
     */
    public EventMembersLimitException() {super("Limit of members in event has been reached.");}
}



