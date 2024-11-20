package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.EventMultimediaNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventMultimediaRepository extends Neo4jRepository<EventMultimediaNode, Long> {
    @Query(value = "match (em:EVENT_MULTIMEDIA)-[:IS_EVENT_MULTIMEDIA]->(e:EVENT) where e.uuid = :eventUUID return em")
    List<EventMultimediaNode> findEventMultimediaByUUID(@Param("eventUUID") UUID eventUUID);

    @Query(value = "match (em:EVENT_MULTIMEDIA)-[:IS_EVENT_MULTIMEDIA]->(e:EVENT)," +
            "(em)-[:IS_PRODUCED_BY]->(u:USER)" +
            "where e.uuid = :eventUUID and u.uuid = :userUUID return em")
    List<EventMultimediaNode> findUserMultimediaInEvent(@Param("userUUID") UUID  userUUID, @Param("eventUUID") UUID eventUUID);

    @Query(value = "MATCH (em:EVENT_MULTIMEDIA {id: $multimediaId}), " +
            "(u:USER {uuid: $userUUID}) " +
            "CREATE (em)-[:IS_PRODUCED_BY]->(u)")
    void attachUserToMultimedia(@Param("userUUID") UUID userUUID, @Param("multimediaId") Long multimediaId);
}