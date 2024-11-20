package com.socialtripper.restapi.nodes;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.LocalDateTime;

@Node("EVENT_MULTIMEDIA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventMultimediaNode {
    @Id
    @GeneratedValue
    private Long id;

    private String multimediaUrl;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    @Relationship(value = "IS_EVENT_MULTIMEDIA", direction = Relationship.Direction.OUTGOING)
    private EventNode event;

    @Relationship(value = "IS_PRODUCED_BY", direction = Relationship.Direction.OUTGOING)
    private UserNode producer;

    public EventMultimediaNode(String multimediaUrl, Double latitude, Double longitude,
                               LocalDateTime timestamp, EventNode event, UserNode producer) {
        this.multimediaUrl = multimediaUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.event = event;
        this.producer = producer;
    }
}
