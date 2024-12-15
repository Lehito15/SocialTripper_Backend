package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wyjątek sygnalizujący, że wskazany przy rejestracji adres email jest już w użytku.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucanie serwer zwraca odpowiedź HTTP z kodem 409 (Conflict).
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email is already associated with another account.")
public class EmailAlreadyInUseException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjatek z wiadomością informująca o konflikcie.
     */
    public EmailAlreadyInUseException() {
        super("Email is already associated with another account.");
    }
}
