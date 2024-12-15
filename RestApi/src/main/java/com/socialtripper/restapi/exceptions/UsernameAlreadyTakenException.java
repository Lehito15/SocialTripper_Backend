package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wyjątek sygnalizujący, że wskazana przy rejestracji nazwa użytkownika jest już w użytku.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucanie serwer zwraca odpowiedź HTTP z kodem 409 (Conflict).
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Account with given name already exists.")
public class UsernameAlreadyTakenException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjatek z wiadomością informująca o konflikcie.
     */
    public UsernameAlreadyTakenException() {
        super("Username already exists");
    }
}
