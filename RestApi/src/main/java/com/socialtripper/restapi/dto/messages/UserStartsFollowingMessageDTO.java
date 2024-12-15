package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości rozpoczęcia obserwacji użytkownika.
 *
 * @param followerUUID globalny, unikalny identyfikator konta użytkownika obserwującego w systemie
 * @param followedUUID globalny, unikalny identyfikator konta użytkownika obserwowanego w systemie
 * @param message wiadomość
 */
public record UserStartsFollowingMessageDTO (UUID followerUUID, UUID followedUUID, String message){
}
