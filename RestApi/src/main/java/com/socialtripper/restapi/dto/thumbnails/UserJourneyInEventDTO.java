package com.socialtripper.restapi.dto.thumbnails;

import com.socialtripper.restapi.dto.requests.EventMultimediaMetadataDTO;
import org.springframework.data.geo.Point;
import java.util.List;

public record UserJourneyInEventDTO (List<Point> pathPoints, List<EventMultimediaMetadataDTO> multimedia) {
}
