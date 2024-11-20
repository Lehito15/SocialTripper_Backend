package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.requests.EventMultimediaMetadataDTO;
import com.socialtripper.restapi.nodes.EventMultimediaNode;
import org.springframework.stereotype.Component;

@Component
public class EventMultimediaMapper {
    public EventMultimediaMetadataDTO toDTO(EventMultimediaNode eventMultimediaNode) {
        if (eventMultimediaNode == null) return null;
        return new EventMultimediaMetadataDTO(
                eventMultimediaNode.getMultimediaUrl(),
                eventMultimediaNode.getLatitude(),
                eventMultimediaNode.getLongitude(),
                eventMultimediaNode.getTimestamp(),
                eventMultimediaNode.getEvent().getUuid(),
                eventMultimediaNode.getProducer().getUuid()
        );
    }
}
