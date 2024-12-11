package com.socialtripper.restapi.dto.messages;

/**
 * Data transfer object dla wiadomości zablokowania postów użytkownika.
 *
 * @param message wiadmość
 * @param postCount liczba zablokowanych postów
 */
public record UserPostsLockedMessageDTO(String message, int postCount) {
}
