package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.EventNode;
import org.neo4j.driver.types.Point;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventNodeRepository extends Neo4jRepository<EventNode, Long> {
    Optional<EventNode> findEventNodeByUuid(UUID uuid);

    @Query(value = "MATCH (u:USER {uuid: $userUuid})-[a:APPLIES_FOR_EVENT]->(e:Event {uuid: $eventUuid}) " +
            "DETACH DELETE a")
    void removeEventRequest(@Param("userUuid") UUID userUuid, @Param("eventUuid") UUID eventUuid);

    @Query(value = "match (u:USER)-[:APPLIES_FOR_EVENT]->(e:EVENT {uuid: $eventUuid}) return u")
    List<Map<String, Object>> findEventRequests(@Param("eventUuid") UUID eventUuid);

    @Query(value = "match (u:USER {uuid: $userUuid})-[m:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid})" +
            "set m.pathPoints = m.pathPoints + $pathPoints")
    void updatePathPoints(@Param("userUuid") UUID userUuid, @Param("eventUuid") UUID eventUuid, @Param("pathPoints") List<Double> pathPoints);
}
