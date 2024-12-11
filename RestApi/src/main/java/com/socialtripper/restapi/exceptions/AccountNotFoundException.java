package com.socialtripper.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.UUID;

/**
 * Wyjątek sygnalizujący, że nie znaleziono konta użytkownika.
 * Pojawienie się wyjątku powoduje, że podczas jego rzucania serwer zwraca odpowiedź HTTP z kodem 404 (Not Found).
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account not found.")
public class AccountNotFoundException extends RuntimeException {
    /**
     * Konstruktor tworzący wyjątek w przypadku gdy konto użytkownika o wskazanym UUID nie istnieje.
     *
     * @param uuid globalny, unikalny identyfikator konta użytkownika w systemie
     */
    public AccountNotFoundException(UUID uuid) {
        super("User with given uuid not found: " + uuid);
    }

    /**
     * Konstruktor tworzący wyjątek w przypadku gdy konto użytkownika o wskazanym adresie email nie istnieje.
     *
     * @param email adres email
     */
    public AccountNotFoundException(String email) {
        super("User with given email not found: " + email);
    }
}
