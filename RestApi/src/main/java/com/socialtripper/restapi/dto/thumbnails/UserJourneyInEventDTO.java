package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.entities.PointDTO;
import com.socialtripper.restapi.dto.requests.EventMultimediaMetadataDTO;

import java.util.List;

/**
 * Data transfer object do podsumowania wydarzenia z perspektywy użytkownika.
 *
 * @param pathPoints lista współrzędnych geografiznych odwiedzonych przez użytkownika - {@link PointDTO}
 * @param multimedia multimedia oraz ich metadane związane z wydarzeniem - {@link EventMultimediaMetadataDTO}
 */
public record UserJourneyInEventDTO (List<PointDTO> pathPoints, List<EventMultimediaMetadataDTO> multimedia) {
}
