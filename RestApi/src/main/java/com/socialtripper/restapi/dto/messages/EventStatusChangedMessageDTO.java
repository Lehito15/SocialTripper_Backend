package com.socialtripper.restapi.dto.messages;

import java.util.UUID;

/**
 * Data transfer object dla wiadomości zmiany statusu wydarzenia.
 *
 * @param groupUUID globalny, unikalny identyfikator grupy w systemie
 * @param status nazwa statusu
 */
public record EventStatusChangedMessageDTO (UUID groupUUID, String status){
}
