package com.socialtripper.restapi.repositories.graph;

import com.socialtripper.restapi.nodes.EventNode;
import com.socialtripper.restapi.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventNodeRepository extends Neo4jRepository<EventNode, String> {
    Optional<EventNode> findEventNodeByUuid(UUID uuid);

    @Query(value = "match (u: USER {uuid: $userUuid}), (e: EVENT {uuid: $eventUuid})" +
            " merge (u)-[:APPLIES_FOR_EVENT]->(e)")
    void createEventRequest(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    @Query(value = "match(u:USER {uuid: $userUuid})-[a:APPLIES_FOR_EVENT]->(e:EVENT {uuid: $eventUuid}) " +
            " delete a")
    void removeEventRequest(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    @Query(value = "match (u: USER {uuid: $userUuid}), (e: EVENT {uuid: $eventUuid})" +
            " merge (u)-[r:IS_EVENT_MEMBER]->(e)" +
            " set r.pathPoints = []")
    void addUserToEvent(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    @Query(value = "match(u:USER {uuid: $userUuid})-[a:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid}) " +
            " delete a")
    void removeUserFromEvent(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    @Query(value = "match (u:USER)-[:APPLIES_FOR_EVENT]->(e:EVENT {uuid: $eventUuid}) return u {.*}")
    List<UserNode> findEventRequests(@Param("eventUuid") String eventUuid);

    @Query(value = "match (u:USER {uuid: $userUuid})-[m:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid}) " +
            "set m.pathPoints = case " +
            "when m.pathPoints is null then $pathPoints " +
            "else m.pathPoints + $pathPoints " +
            "end")
    void updatePathPoints(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid, @Param("pathPoints") List<Double> pathPoints);

    @Query(value = "match (u:USER {uuid: $userUuid})-[m:IS_EVENT_MEMBER]->(e:EVENT {uuid: $eventUuid}) " +
            " return m.pathPoints")
    List<Double> getPathPoints(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    @Query(value = "match (e: EVENT)-[:IS_GROUP_EVENT]->(g: GROUP {uuid: $groupUuid})" +
            " return e {.*}")
    List<EventNode> getGroupEvents(@Param("groupUuid") String groupUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})-[r:IS_EVENT_MEMBER]->(e: EVENT {uuid: $eventUuid})" +
            " return count(r) > 0 as isMember")
    boolean isEventMember(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);

    @Query(value = "match (u: USER)-[:IS_EVENT_MEMBER]->(e: EVENT {uuid: $eventUuid})" +
            " return u {.*}")
    List<UserNode> findEventMembers(@Param("eventUuid") String eventUuid);

    @Query(value = "match (u: USER {uuid: $userUuid})-[r: APPLIES_FOR_EVENT]->(e: EVENT {uuid: $eventUuid})" +
            " return count(r) > 0 as isRequestSent")
    boolean isEventRequestSent(@Param("userUuid") String userUuid, @Param("eventUuid") String eventUuid);
}
