package com.socialtripper.restapi.dto.requests;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data transfer object dla metadanych multimediów.
 *
 * @param multimediaUrl link do multimedium
 * @param latitude szerokość geograficzna wykonania
 * @param longitude długość geograficzna wykonania
 * @param timestamp znacznik czasowy wykonania
 * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
 * @param eventUUID globalny, unikalny identyfikator wydarzenia w systemie
 */
public record EventMultimediaMetadataDTO(String multimediaUrl, double latitude, double longitude,
                                         LocalDateTime timestamp, UUID userUUID, UUID eventUUID) {
}
