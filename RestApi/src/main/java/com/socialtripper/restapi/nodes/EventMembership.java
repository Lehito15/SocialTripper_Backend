package com.socialtripper.restapi.nodes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import java.util.List;


@RelationshipProperties
@Getter
@Setter
public class EventMembership {
    @Id
    @GeneratedValue
    private String id;

    List<Double> pathPoints;

    @TargetNode
    EventNode event;

    public EventMembership(List<Double> pathPoints, EventNode event) {
        this.pathPoints = pathPoints;
        this.event = event;
    }
}
