package com.socialtripper.restapi.dto.messages;

/**
 * Data transfer object dla wiadomości dezaktywacji postów użytkownika.
 *
 * @param message wiadomość
 * @param postCount liczba dezaktywowanych postów
 */
public record UserPostsExpiredMessageDTO(String message, int postCount) {
}
