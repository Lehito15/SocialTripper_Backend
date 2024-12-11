package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.requests.EventMultimediaMetadataDTO;
import com.socialtripper.restapi.nodes.EventMultimediaNode;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy węzłem multimediów {@link EventMultimediaNode},
 * a data transfer object {@link EventMultimediaMetadataDTO}.
 */
@Component
public class EventMultimediaMapper {
    /**
     * Metoda mapująca węzeł multimedium do data transfer object.
     *
     * @param eventMultimediaNode węzeł multimedium
     * @return data transfer object metadanych multimedium
     */
    public EventMultimediaMetadataDTO toDTO(EventMultimediaNode eventMultimediaNode) {
        if (eventMultimediaNode == null) return null;
        return new EventMultimediaMetadataDTO(
                eventMultimediaNode.getMultimediaUrl(),
                eventMultimediaNode.getLatitude(),
                eventMultimediaNode.getLongitude(),
                eventMultimediaNode.getTimestamp(),
                eventMultimediaNode.getProducer().getUuid(),
                eventMultimediaNode.getEvent().getUuid()
        );
    }

    /**
     * Meotda mapująca węzeł multimedium do data transfer object.
     * Ustawiane są UUID autora multimedium oraz wydarzenia, w którym zostało ono wygenerowane.
     *
     * @param eventMultimediaNode węzełm multimedium
     * @param userUUID globalny, unikalny identyfikator użytkownika w systemie
     * @param eventUUID globalny, unikanlny identyfikator wydarzenia w systemie
     * @return data transfer object metadanych multimedium
     */
    public EventMultimediaMetadataDTO toDTO(EventMultimediaNode eventMultimediaNode, UUID userUUID, UUID eventUUID) {
        if (eventMultimediaNode == null) return null;
        return new EventMultimediaMetadataDTO(
                eventMultimediaNode.getMultimediaUrl(),
                eventMultimediaNode.getLatitude(),
                eventMultimediaNode.getLongitude(),
                eventMultimediaNode.getTimestamp(),
                userUUID,
                eventUUID
        );
    }
}

