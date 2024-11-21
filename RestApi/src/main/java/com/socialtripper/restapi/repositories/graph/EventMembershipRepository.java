package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.EventMembership;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventMembershipRepository extends Neo4jRepository<EventMembership, String> {
    @Query(value = "match (u:USER {uuid: $userUuid})-[m:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid})" +
            "return m")
    Optional<EventMembership> getUserMembershipInEvent(@Param("userUuid") UUID userUuid, @Param("eventUuid") UUID eventUuid);
}
