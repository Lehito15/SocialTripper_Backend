package com.socialtripper.restapi.dto.requests;

import java.util.UUID;

/**
 * Data transfer object dla relacji wysłania prośby o dołączenie do wydarzenia.
 *
 * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
 * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
 * @param message wiadomość
 */
public record UserRequestEventDTO (UUID userUUID, UUID eventUUID, String message){
}
